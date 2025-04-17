package com.hari.gatherspace.features.element;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementResponse {
  private String id;

  public ElementResponse(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
