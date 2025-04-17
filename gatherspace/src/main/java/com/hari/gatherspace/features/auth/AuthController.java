package com.hari.gatherspace.features.auth;

import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.features.user.Role;
import com.hari.gatherspace.features.user.User;
import com.hari.gatherspace.features.user.UserDTO;
import com.hari.gatherspace.features.user.UserService;
import com.hari.gatherspace.features.user.UserSigninDTO;
import com.hari.gatherspace.features.usersession.UserSessionDto;
import com.hari.gatherspace.features.usersession.UserSessionService;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

  @Autowired private UserService userService;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserSessionService userSessionService;

  @PostMapping("/signup")
  public ResponseEntity<Object> signup(@RequestBody UserDTO user) {
    if (user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
      return ResponseEntity.badRequest().body("Username, password, and role are required.");
    }

    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    newUser.setRole(Objects.equals(user.getRole(), "Admin") ? Role.Admin : Role.User);

    try {
      User savedUser = userService.saveUser(newUser);
      return ResponseEntity.ok(Map.of("userId", savedUser.getId()));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(
      @RequestBody UserSigninDTO user,
      @RequestHeader("User-Agent") String userAgent) { // Read device info from header
    log.info("Login attempt for: {}", user.getUsername());

    Optional<User> userOptional = userService.getUser(user.getUsername());
    if (userOptional.isPresent()) {
      User user1 = userOptional.get();
      if (passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
        // Generate tokens using the new overloaded methods
        String accessToken =
            jwtUtil.generateAccessToken(
                user1.getUsername(),
                user1.getId(),
                user1.getRole().toString() // convert enum to string, e.g., "Admin"
                );
        String refreshToken =
            jwtUtil.generateRefreshToken(
                user1.getUsername(), user1.getId(), user1.getRole().toString());

        // Determine refresh token expiration; for example, 7 days from now
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(7);

        // Create a new user session DTO and set its properties
        UserSessionDto sessionDto = new UserSessionDto();
        sessionDto.setUserId(user1.getId());
        sessionDto.setRefreshToken(refreshToken);
        sessionDto.setExpiresAt(expiresAt);
        sessionDto.setDeviceInfo(userAgent); // Device info from request header

        // Save the session via the service
        userSessionService.addSession(sessionDto);

        // Return the tokens
        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
      }
    }
    return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password."));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<Map<String, String>> refreshAccessToken(
      @RequestHeader("Authorization") String refreshToken) {
    if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
      refreshToken = refreshToken.substring(7);
      try {
        String username = jwtUtil.extractUsername(refreshToken);
        // For refresh tokens, you could also re-embed claims if needed
        String newAccessToken =
            jwtUtil.generateAccessToken(
                username, jwtUtil.extractUserId(refreshToken), jwtUtil.extractRole(refreshToken));
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
      } catch (Exception e) {
        return ResponseEntity.status(403).body(Map.of("message", "Invalid refresh token"));
      }
    }
    return ResponseEntity.badRequest().body(Map.of("message", "Refresh token is required"));
  }
}
