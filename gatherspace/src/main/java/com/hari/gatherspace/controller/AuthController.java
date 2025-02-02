package com.hari.gatherspace.controller;

import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.dto.UserDTO;
import com.hari.gatherspace.dto.UserSigninDTO;
import com.hari.gatherspace.model.Role;
import com.hari.gatherspace.model.User;
import com.hari.gatherspace.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<Map<String, String>> login(@RequestBody UserSigninDTO user) {
        System.out.println("Login attempt for: {}" + user.getUsername());

        Optional<User> userOptional = userService.getUser(user.getUsername());
        if (userOptional.isPresent()) {
            User user1 = userOptional.get();
            if (passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
                String accessToken = jwtUtil.generateAccessToken(user1.getUsername());
                String refreshToken = jwtUtil.generateRefreshToken(user1.getUsername());

                return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password."));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
            try {
                String username = jwtUtil.extractUsername(refreshToken);
                String newAccessToken = jwtUtil.generateAccessToken(username);
                return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
            } catch (Exception e) {
                return ResponseEntity.status(403).body(Map.of("message", "Invalid refresh token"));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Refresh token is required"));
    }
}
