package com.hari.gatherspace.features.element;

import lombok.Data;

@Data
public class ElementAllDto {
  private String id;
  private Integer width;
  private Integer height;
  private boolean staticValue;
  private String imageUrl;
}
