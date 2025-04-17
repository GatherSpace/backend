package com.hari.gatherspace.features.map;

import com.hari.gatherspace.features.element.DefaultElement;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateMapRequest {

  private String thumbnail;

  private String dimensions;

  private String name;

  private List<DefaultElement> defaultElements;

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
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

  public List<DefaultElement> getDefaultElements() {
    return defaultElements;
  }

  public void setDefaultElements(List<DefaultElement> defaultElements) {
    this.defaultElements = defaultElements;
  }
}
