package com.hari.gatherspace.features.usersession;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // The associated user’s ID (you can also use a relation to a User entity)
  @Column(nullable = false, length = 512)
  private String userId;

  @Column(nullable = false, unique = true, length = 512)
  private String refreshToken;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  @Column(nullable = false)
  private Boolean isValid = true;

  @Column(length = 256)
  private String deviceInfo;

  @Column(nullable = false)
  private LocalDateTime loginTime;
}
