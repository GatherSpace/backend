package com.hari.gatherspace.service.ws;

import com.hari.gatherspace.model.ws.UserWs;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserWsService {

    private final Map<String, UserWs> activeUsers = new ConcurrentHashMap<>();

    public UserWs createUser(String userId, WebSocketSession session) {
        UserWs user = new UserWs(userId, session);
        activeUsers.put(userId, user);
        return user;
    }

    public UserWs getUser(String userId) {
        return activeUsers.get(userId);
    }

    public void removeUser(String userId) {
        activeUsers.remove(userId);
    }

    // ... other methods for user management
}
