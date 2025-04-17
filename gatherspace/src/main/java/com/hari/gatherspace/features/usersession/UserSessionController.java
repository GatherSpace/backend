package com.hari.gatherspace.features.usersession;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usersessions")
public class UserSessionController {

  private final UserSessionService userSessionService;

  @Autowired
  public UserSessionController(UserSessionService userSessionService) {
    this.userSessionService = userSessionService;
  }

  // Get all sessions
  @GetMapping
  public List<UserSessionDto> getAllSessions() {
    return userSessionService.getAllSessions();
  }

  // Get session by its ID
  @GetMapping("/{sessionId}")
  public UserSessionDto getSessionById(@PathVariable Long sessionId) {
    return userSessionService.getSessionById(sessionId);
  }

  // Get sessions for a given user
  @GetMapping("/user/{userId}")
  public List<UserSessionDto> getSessionsByUserId(@PathVariable String userId) {
    return userSessionService.getSessionsByUserId(userId);
  }

  // Create a new session (e.g., when issuing a new refresh token)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserSessionDto addSession(@RequestBody UserSessionDto userSessionDto) {
    return userSessionService.addSession(userSessionDto);
  }

  // Delete a session by ID
  @DeleteMapping("/{id}")
  public UserSessionDto deleteSession(@PathVariable Long id) {
    return userSessionService.deleteSession(id);
  }

  // Invalidate a session by refresh token
  @PostMapping("/invalidate/{token}")
  public UserSessionDto invalidateSession(@PathVariable String token) {
    return userSessionService.invalidate(token);
  }

  // Invalidate all sessions for a given user
  @PostMapping("/invalidateAll/{userId}")
  public void invalidateAllSessions(@PathVariable String userId) {
    userSessionService.invalidateAllSessionsForUser(userId);
  }

  // Check whether a refresh token is still valid
  @GetMapping("/validate/{token}")
  public boolean isTokenValid(@PathVariable String token) {
    return userSessionService.isTokenValid(token);
  }

  // Optionally, flush (delete) all expired sessions
  @PostMapping("/flush")
  public void flushExpiredSessions() {
    userSessionService.flushExpiredSessions();
  }
}
