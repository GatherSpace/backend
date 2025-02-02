package com.hari.gatherspace.service;

import com.hari.gatherspace.dto.UserSessionDto;

import java.util.List;

public interface UserSessionService {
    List<UserSessionDto> getAllSessions();
    UserSessionDto getSessionById(Long sessionId);
    List<UserSessionDto> getSessionsByUserId(Long userId);
    UserSessionDto addSession(UserSessionDto userSessionDto);
    UserSessionDto deleteSession(Long id);
    UserSessionDto invalidate(String token);
    void invalidateAllSessionsForUser(Long userId);
    boolean isTokenValid(String token);
    void flushExpiredSessions();
}
