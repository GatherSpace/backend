package com.hari.gatherspace.features.usersession;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserSessionDto {
  private Long id;
  private String userId;
  private String refreshToken;
  private LocalDateTime expiresAt;
  private Boolean isValid;
  private String deviceInfo;
  private LocalDateTime loginTime = LocalDateTime.now();

}
