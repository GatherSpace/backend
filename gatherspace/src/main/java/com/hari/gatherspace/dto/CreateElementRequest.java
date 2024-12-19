package com.hari.gatherspace.dto;


import lombok.Getter;
import lombok.Setter;


public class CreateElementRequest {

    private String imageUrl;


    private Integer width;


    private Integer height;


    private Boolean staticValue;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getStaticValue() {
        return staticValue;
    }

    public void setStaticValue(Boolean staticValue) {
        this.staticValue = staticValue;
    }
}