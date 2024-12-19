package com.hari.gatherspace.dto;

public class ElementAllDto {
    private String id;
    private Integer width;
    private Integer height;
    private boolean staticValue;
    private String imageUrl;
  // You might simplify this further if needed

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isStaticValue() {
        return staticValue;
    }

    public void setStaticValue(boolean staticValue) {
        this.staticValue = staticValue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    // ... constructors, getters, setters
}