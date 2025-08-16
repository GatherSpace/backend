package com.hari.gatherspace.features.space;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.features.user.User;
import com.hari.gatherspace.features.user.UserService;
import com.hari.gatherspace.features.user.UserWs;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class SpaceWsController extends TextWebSocketHandler {

  private final Map<String, UserWs> connectedUsers = new ConcurrentHashMap<>();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final JwtUtil jwtUtil;
  private final UserService userService;
  private final SpaceService spaceService;
  private final SpaceWsService spaceWsService;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("WebSocket connection established: " + session.getId());
    UserWs userWs = new UserWs(session);
    connectedUsers.put(userWs.getId(), userWs);
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    try {
      JsonNode jsonNode = objectMapper.readTree(message.getPayload());
      String type = jsonNode.get("type").asText();
      JsonNode payload = jsonNode.get("payload");

      UserWs userWs = findUserBySessionId(session.getId());
      if (userWs == null) {
        System.out.println("User not found for session: " + session.getId());
        return;
      }

      switch (type) {
        case "join":
          handleJoin(userWs, payload);
          break;
        case "move":
          handleMove(userWs, payload);
          break;
        case "message":
          handleChat(userWs, payload);
          break;
        default:
          System.out.println("Unknown message type: " + type);
      }
    } catch (IOException e) {
      System.err.println("Error handling WebSocket message: " + e.getMessage());
    }
  }

  private void handleChat(UserWs userWs, JsonNode payload) throws IOException {
    String message = payload.get("message").asText();
    String sendToId = payload.get("userId").asText();
    Map<String, Object> messagePayload =
        Map.of("type", "message", "payload", Map.of("message", message));
    spaceWsService.sendMessageToUser(userWs, messagePayload, sendToId);
  }

  private void handleJoin(UserWs userWs, JsonNode payload) throws IOException {
    String token = payload.get("token").asText();
    String spaceId = payload.get("spaceId").asText();
    String userNameFromToken = jwtUtil.extractUsername(token); // Assuming username is the userId

    User user = userService.getUser(userNameFromToken).orElse(null);
    if (user == null) {
      System.out.println("User not found with username from token: " + userNameFromToken);
      userWs.getSession().close(CloseStatus.BAD_DATA);
      return;
    }
    userWs.setUserId(user.getId()); // Set the authenticated user ID

    Space space = spaceService.getSpaceById(spaceId);
    if (space == null) {
      System.out.println("Space not found with id: " + spaceId);
      try {
        userWs.getSession().close(CloseStatus.BAD_DATA);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    }

    spaceWsService.addUserToRoom(spaceId, userWs);

    // Generate a random spawn location within the space dimensions
    int spawnX = (int) (Math.random() * space.getWidth());
    int spawnY = (int) (Math.random() * space.getHeight());
    userWs.setX(spawnX);
    userWs.setY(spawnY);

    // Send confirmation to the joining user
    Map<String, Object> joinConfirmationPayload =
        Map.of(
            "type",
            "space-joined",
            "payload",
            Map.of(
                "userId", userWs.getUserId(),
                "spawn", Map.of("x", spawnX, "y", spawnY),
                "users",
                    spaceWsService.getUsersInRoom(spaceId).stream()
                        .filter(u -> !u.getId().equals(userWs.getId()))
                        .map(u -> Map.of("userId", u.getUserId(), "x", u.getX(), "y", u.getY()))
                        .collect(Collectors.toList())));
    userWs.send(convertToJson(joinConfirmationPayload));

    // Broadcast user joined message to other users in the room
    Map<String, Object> userJoinedPayload =
        Map.of(
            "type",
            "user-joined",
            "payload",
            Map.of(
                "userId", userWs.getUserId(),
                "x", userWs.getX(),
                "y", userWs.getY()));
    spaceWsService.broadcast(userJoinedPayload, userWs, spaceId, "user-joined");
  }

  private void handleMove(UserWs userWs, JsonNode payload) {
    int moveX = payload.get("x").asInt();
    int moveY = payload.get("y").asInt();

    int xDisplacement = Math.abs(userWs.getX() - moveX);
    int yDisplacement = Math.abs(userWs.getY() - moveY);
    System.out.println("xDisplacement: " + xDisplacement + " yDisplacement: " + yDisplacement);
    if ((xDisplacement == 1 && yDisplacement == 0) || (xDisplacement == 0 && yDisplacement == 1)) {
      userWs.setX(moveX);
      userWs.setY(moveY);
      System.out.println("User moved to: " + userWs.getX() + ", " + userWs.getY());
      // Broadcast movement to other users in the room
      Map<String, Object> movementPayload =
          Map.of(
              "type",
              "movement",
              "payload",
              Map.of(
                  "userId", userWs.getUserId(),
                  "x", userWs.getX(),
                  "y", userWs.getY()));
      spaceWsService.broadcast(movementPayload, userWs, userWs.getSpaceId(), "movement");
    } else {
      // Send movement rejected message back to the user
      Map<String, Object> rejectPayload =
          Map.of(
              "type",
              "movement-rejected",
              "payload",
              Map.of(
                  "x", userWs.getX(),
                  "y", userWs.getY()));
      userWs.send(convertToJson(rejectPayload));
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    UserWs userWs = findUserBySessionId(session.getId());
    if (userWs != null) {
      System.out.println("WebSocket connection closed for user: " + userWs.getId());
      connectedUsers.remove(userWs.getId());
      spaceWsService.removeUserFromRoom(userWs, userWs.getSpaceId());

      // Broadcast user left message
      Map<String, Object> userLeftPayload =
          Map.of("type", "user-left", "payload", Map.of("userId", userWs.getUserId()));
      spaceWsService.broadcast(userLeftPayload, userWs, userWs.getSpaceId(), "user-left");
    }
  }

  private UserWs findUserBySessionId(String sessionId) {
    return connectedUsers.values().stream()
        .filter(user -> user.getSession().getId().equals(sessionId))
        .findFirst()
        .orElse(null);
  }

  private String convertToJson(Map<String, Object> payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (IOException e) {
      System.err.println("Error converting payload to JSON: " + e.getMessage());
      return null;
    }
  }
}
