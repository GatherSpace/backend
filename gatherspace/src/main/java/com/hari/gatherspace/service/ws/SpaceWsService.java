package com.hari.gatherspace.service.ws;

import com.hari.gatherspace.model.ws.SpaceWs;
import com.hari.gatherspace.model.ws.UserWs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpaceWsService {
    private final Map<String, SpaceWs> spaces = new ConcurrentHashMap<>();

    public SpaceWs getSpace(String spaceId) {
        return spaces.computeIfAbsent(spaceId, id -> new SpaceWs(id));
    }

    public void addUserToSpace(String spaceId, UserWs user) {
        SpaceWs space = getSpace(spaceId);
        space.addUser(user);
    }

    public void removeUserFromSpace(String spaceId, String userId) {
        SpaceWs space = getSpace(spaceId);
        space.removeUser(userId);
    }

    public void updateUserPosition(String spaceId, String userId, int x, int y) {
        SpaceWs space = getSpace(spaceId);
        UserWs user = space.getUser(userId);
        if (user != null) {
            user.setPosition(x, y);
        }
    }

    public boolean isValidMove(String spaceId, String userId, int newX, int newY) {
        SpaceWs space = getSpace(spaceId);
        UserWs user = space.getUser(userId);
        if (user != null) {
            return true;
        }
        return false;
    }
}
