package com.hari.gatherspace.model.ws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpaceWs {

    private String id;
    private Map<String, UserWs> users = new ConcurrentHashMap<>();


    public SpaceWs(String id) {
        this.id = id;
    }
    public void addUser(UserWs user) {
        users.put(user.getId(), user);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, UserWs> getUsers() {
        return users;
    }

    public void setUsers(Map<String, UserWs> users) {
        this.users = users;
    }

    public void removeUser(String userId) {
        users.remove(userId);
    }

    public UserWs getUser(String userId) {
        return users.get(userId);
    }
}
