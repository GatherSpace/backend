package com.hari.gatherspace.features.usersession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

  Optional<UserSession> findByRefreshToken(String refreshToken);

  List<UserSession> findByUserId(String userId);

  List<UserSession> findByExpiresAtBefore(LocalDateTime now);
}
