package com.hari.gatherspace.features.avatar;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvatarDto {
  private String id;
  private String imageUrl;
  private String name;
  private List<String> userId;
}
