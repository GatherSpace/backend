package com.hari.gatherspace.features.avatar;

import java.util.List;

public class AvatarDto {
    private String id;
    private String imageUrl;
    private String name;
//    private String userId;
    private List<String> userId;


    public AvatarDto(String id, String imageUrl, String name, List<String> userId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUserId() {
        return userId;

    }
    public void setUserId(List<String> userId) {
        this.userId = userId;
    }

    /* public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    } */

// Notice: No 'users' field here to avoid circularity
}