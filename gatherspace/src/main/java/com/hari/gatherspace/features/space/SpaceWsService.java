package com.hari.gatherspace.features.space;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hari.gatherspace.features.user.UserWs;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SpaceWsService {

  // Stores rooms and the users within them
  private final Map<String, List<UserWs>> rooms = new ConcurrentHashMap<>();
  private final ObjectMapper objectMapper = new ObjectMapper();

  // Method to add a user to a room
  public void addUserToRoom(String spaceId, UserWs user) {
    rooms.computeIfAbsent(spaceId, k -> new java.util.ArrayList<>()).add(user);
    user.setSpaceId(spaceId);
      log.info("User {} added to room {}", user.getId(), spaceId);
  }

  // Method to remove a user from a room
  public void removeUserFromRoom(UserWs user, String spaceId) {
    if (rooms.containsKey(spaceId)) {
      rooms.get(spaceId).removeIf(u -> u.getId().equals(user.getId()));
      if (rooms.get(spaceId).isEmpty()) {
        rooms.remove(spaceId); // Remove the room if it's empty
      }
        log.info("User {} removed from room {}", user.getId(), spaceId);
    }
  }

  public void sendMessageToUser(UserWs user, Object messagePayload, String userId) {
    String roomId = user.getSpaceId();
    if (rooms.containsKey(roomId)) {
      String messageJson;
      try {
        messageJson = objectMapper.writeValueAsString(messagePayload);
          log.info("Sending message to user {} in room {}: {}", user.getId(), roomId, messageJson);
        for (UserWs u : rooms.get(roomId)) {
          if (u.getUserId().equals(userId)) {
              log.info("message sent to {}", u.getId());
            u.send(messageJson);
            return;
          }
        }
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
  }

  // Method to broadcast a message to all users in a room except the sender
  public void broadcast(Object messagePayload, UserWs sender, String roomId, String messageType) {
    if (rooms.containsKey(roomId)) {
      String messageJson;
      try {
        messageJson = objectMapper.writeValueAsString(messagePayload);
        log.info("Broadcasting message to room {}: {}", roomId, messageJson);
        for (UserWs user : rooms.get(roomId)) {
          if (messageType.equals("movement")) {
            user.send(messageJson);
          } else if (!user.getId().equals(sender.getId())) {
            log.info("message sent to ");
            user.send(messageJson);
          }
        }
      } catch (IOException e) {
          log.error("Error broadcasting message to room {}: {}", roomId, e.getMessage());
      }
    }
  }

  // Get all users in a specific room
  public List<UserWs> getUsersInRoom(String spaceId) {
    return rooms.getOrDefault(spaceId, java.util.Collections.emptyList());
  }
}
