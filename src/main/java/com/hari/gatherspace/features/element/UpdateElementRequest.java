package com.hari.gatherspace.features.element;

public class UpdateElementRequest {

  private String imageUrl;

  public UpdateElementRequest() {}

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
