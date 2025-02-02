package com.hari.gatherspace.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserSessionDto {
    private Long id;
    private Long userId;
    private String refreshToken;
    private LocalDateTime expiresAt;
    private Boolean isValid;
}
