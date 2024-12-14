package com.hari.gatherspace.controller.ws;

import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.dto.UserDTO;
import com.hari.gatherspace.dto.UserSigninDTO;
import com.hari.gatherspace.model.Role;
import com.hari.gatherspace.model.User;
import com.hari.gatherspace.repository.UserRepository;
import com.hari.gatherspace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

// /api/signup
// /api/login
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UserDTO user) {
        // TODO: Implement signup logic
        if(user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
            return ResponseEntity.badRequest().body("Username, password and role are required.");
        }
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setRole(Objects.equals(user.getRole(), "Admin") ? Role.Admin : Role.User);

        try {
            User user2 = userService.saveUser(user1);

            System.out.println("control reaches here");
            return ResponseEntity.accepted().body(Map.of("message", "User created successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }



    }


    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signin(@RequestBody UserSigninDTO user) {
        // TODO: Implement login logic
        System.out.println("control reaches here");
        try {
            Optional<User> userOptional = userService.getUser(user.getUsername());
            if (userOptional.isPresent()) {
                User user1 = userOptional.get();

                if (passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
                    jwtUtil = new JwtUtil();
                    System.out.println("control reaches here token not created");
                    Map<String, String> claims = Map.of("role", user1.getRole().toString());
                    String token = jwtUtil.generateToken(claims, user1.getUsername());
                    Map<String, String> response = Map.of("token", token);
                    System.out.println("control reaches here token created");
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password."));

                }
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
        }



}
