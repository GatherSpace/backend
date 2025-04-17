package com.hari.gatherspace.features.space;

import java.util.List;
import lombok.Data;

@Data
public class SpaceDTO {
  private String id;
  private String dimensions;
  private String name;
  private String thumbnail;
  private String creatorId;
  private List<SpaceElements> elements;
}
