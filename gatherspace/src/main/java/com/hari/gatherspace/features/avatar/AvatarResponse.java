package com.hari.gatherspace.features.avatar;


public class AvatarResponse {
    private String avatarId;

    public AvatarResponse(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }
}