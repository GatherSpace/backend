package com.hari.gatherspace.features.avatar;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvatarUserDto {
  private String name;
  private String avatarUrl;
  private String userId;
  private String avatarId;
}
