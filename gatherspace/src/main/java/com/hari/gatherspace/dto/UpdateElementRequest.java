package com.hari.gatherspace.dto;


import lombok.Getter;
import lombok.Setter;


public class UpdateElementRequest {

    private String imageUrl;

    public UpdateElementRequest() {

    }

    public UpdateElementRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}