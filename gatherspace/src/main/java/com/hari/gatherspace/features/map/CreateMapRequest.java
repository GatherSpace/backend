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
}
