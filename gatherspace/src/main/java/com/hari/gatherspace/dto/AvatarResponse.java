package com.hari.gatherspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


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