package com.hari.gatherspace.features.avatar;

public class AvatarUserDto {
    private String name;
    private String avatarUrl;
    private String userId;
    private String avatarId;

    public AvatarUserDto(String name, String avatarUrl, String userId, String avatarId) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.userId = userId;
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }
}
