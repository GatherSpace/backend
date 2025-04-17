package com.hari.gatherspace.features.element;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DefaultElement {

  private String elementId;
  private Integer x;
  private Integer y;
}
