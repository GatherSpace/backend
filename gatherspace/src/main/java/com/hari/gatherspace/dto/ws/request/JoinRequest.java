package com.hari.gatherspace.dto.ws.request;

public class JoinRequest {
    private String spaceId;
    private String token;

    // Getters and setters


    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}