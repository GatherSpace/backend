package com.hari.gatherspace.controller.ws;



import com.hari.gatherspace.dto.ws.request.JoinRequest;
import com.hari.gatherspace.dto.ws.request.MoveRequest;
import com.hari.gatherspace.dto.ws.response.JoinResponse;
import com.hari.gatherspace.dto.ws.response.MovementResponse;
import com.hari.gatherspace.dto.ws.response.MovementRejectedResponse;
import com.hari.gatherspace.dto.ws.response.UserJoinResponse;
import com.hari.gatherspace.dto.ws.response.UserLeftResponse;
import com.hari.gatherspace.model.User;
import com.hari.gatherspace.model.ws.UserWs;
import com.hari.gatherspace.service.SpaceService;
import com.hari.gatherspace.service.UserService;
import com.hari.gatherspace.service.ws.SpaceWsService;
import com.hari.gatherspace.service.ws.UserWsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

@Controller
public class SpaceController {
    private final SimpMessagingTemplate messagingTemplate;
    private final SpaceWsService spaceService;
    private final UserWsService userService;

    @Autowired
    public SpaceController(SimpMessagingTemplate messagingTemplate, SpaceWsService spaceService, UserWsService userService) {
        this.messagingTemplate = messagingTemplate;
        this.spaceService = spaceService;
        this.userService = userService;
    }

    @MessageMapping("/join") // Handles messages to /app/join
    public void joinSpace(@Payload JoinRequest joinRequest, SimpMessageHeaderAccessor headerAccessor) {
        String spaceId = joinRequest.getSpaceId();
        String userId = headerAccessor.getSessionId(); // Or extract from JWT in joinRequest.getToken()

        // 1. Validate User and Space (using SpaceService, UserService, potentially AuthService)
        // 2. Create User (if needed) and assign to the Space
        UserWs user = userService.createUser(userId, headerAccessor.getSession().getNativeSession(WebSocketSession.class));
        spaceService.addUserToSpace(spaceId, user);

        // 3. Notify the user that they joined successfully
        JoinResponse joinResponse = new JoinResponse();
        // ... set spawn point, users in the space
        messagingTemplate.convertAndSendToUser(userId, "/topic/space-joined", joinResponse);

        // 4. Broadcast to other users in the space
        UserJoinResponse userJoinResponse = new UserJoinResponse();
        // ... set userId, x, y
        messagingTemplate.convertAndSend("/topic/space/" + spaceId, userJoinResponse);
    }

    @MessageMapping("/move") // Handles messages to /app/move
    public void move(@Payload MoveRequest moveRequest, SimpMessageHeaderAccessor headerAccessor) {
        String userId = headerAccessor.getSessionId();
        UserWs user = userService.getUser(userId);
        String spaceId = user.getSpaceId();
        int newX = moveRequest.getX();
        int newY = moveRequest.getY();

        // 1. Check for collisions (SpaceService)
        if (spaceService.isValidMove(spaceId, userId, newX, newY)) {
            // 2. Update user's position (SpaceService)
            spaceService.updateUserPosition(spaceId, userId, newX, newY);

            // 3. Broadcast movement to other users
            MovementResponse movementResponse = new MovementResponse();
            movementResponse.setUserId(userId);
            movementResponse.setX(newX);
            movementResponse.setY(newY);
            messagingTemplate.convertAndSend("/topic/space/" + spaceId, movementResponse);
        } else {
            // 4. Send movement rejection
            MovementRejectedResponse rejectedResponse = new MovementRejectedResponse();
            // ... set x, y to user's current valid position
            messagingTemplate.convertAndSendToUser(userId, "/topic/movement-rejected", rejectedResponse);
        }
    }

    // Add a method to handle user disconnect
    public void leaveSpace(String userId) {
        UserWs user = userService.getUser(userId);
        if (user != null) {
            String spaceId = user.getSpaceId();
            spaceService.removeUserFromSpace(spaceId, userId);
            userService.removeUser(userId);
            // Broadcast user left event
            UserLeftResponse userLeftResponse = new UserLeftResponse();
            userLeftResponse.setUserId(userId);
            messagingTemplate.convertAndSend("/topic/space/" + spaceId, userLeftResponse);
        }
    }

    // ... other @MessageMapping methods for other event types
}
