package com.hari.gatherspace.features.usersession;

import java.util.List;

public interface UserSessionService {
  List<UserSessionDto> getAllSessions();

  UserSessionDto getSessionById(Long sessionId);

  List<UserSessionDto> getSessionsByUserId(String userId);

  UserSessionDto addSession(UserSessionDto userSessionDto);

  UserSessionDto deleteSession(Long id);

  UserSessionDto invalidate(String token);

  void invalidateAllSessionsForUser(String userId);

  boolean isTokenValid(String token);

  void flushExpiredSessions();
}
