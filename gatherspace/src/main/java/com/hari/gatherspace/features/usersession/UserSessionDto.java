package com.hari.gatherspace.features.usersession;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSessionDto {
    private Long id;
    private String userId;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private Boolean isValid;
    private String deviceInfo;
}
