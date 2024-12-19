package com.hari.gatherspace.dto.ws.response;

import com.hari.gatherspace.model.ws.Point;
import com.hari.gatherspace.model.ws.UserWs;

import java.util.List;

public class JoinResponse {
    private Point spawn;
    private List<UserWs> users;

    // Getters and setters


    public Point getSpawn() {
        return spawn;
    }

    public void setSpawn(Point spawn) {
        this.spawn = spawn;
    }

    public List<UserWs> getUsers() {
        return users;
    }

    public void setUsers(List<UserWs> users) {
        this.users = users;
    }
}

