package com.hari.gatherspace.features.space;

import java.util.List;

public class SpaceDTO {

  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDimensions() {
    return dimensions;
  }

  public void setDimensions(String dimensions) {
    this.dimensions = dimensions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }

  public List<SpaceElements> getElements() {
    return elements;
  }

  public void setElements(List<SpaceElements> elements) {
    this.elements = elements;
  }

  private String dimensions;
  private String name;

  private String thumbnail;

  private String creatorId;

  private List<SpaceElements> elements;
}
