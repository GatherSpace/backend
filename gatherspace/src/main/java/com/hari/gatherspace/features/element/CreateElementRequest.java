package com.hari.gatherspace.features.element;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateElementRequest {

  private String imageUrl;

  private Integer width;

  private Integer height;

  private Boolean staticValue;
}
