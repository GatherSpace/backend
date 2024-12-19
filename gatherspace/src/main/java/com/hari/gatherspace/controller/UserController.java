package com.hari.gatherspace.controller;


import java.util.List;
import java.util.Map;

import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.dto.AvatarDto;
import com.hari.gatherspace.model.Avatar;
import com.hari.gatherspace.model.Role;
import com.hari.gatherspace.model.User;
import com.hari.gatherspace.service.AvatarService;
import com.hari.gatherspace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AvatarService avatarService;
    private JwtUtil jwtUtil;

    UserController() {
        this.jwtUtil = new JwtUtil();
    }

    @PostMapping("/metadata")
    public ResponseEntity<Map<String, String>> saveUserMetadata(HttpServletRequest request,@RequestBody Map<String, String> metadata) {
        // TODO: Save user metadata
        String token = jwtUtil.extractToken(request);

        String username = jwtUtil.extractUsername(token);

        User user = userService.getUser(username).get();

        user.setAvatarId(metadata.get("avatarId"));

        userService.updateUser(user);


        return ResponseEntity.ok().build();
    }


    /*

    {
  "avatars": [{
     "id": 1,
     "imageUrl": "https://image.com/avatar1.png",
     "name": "Timmy"
  }]
}
     */

    @GetMapping("/avatars")
    public ResponseEntity<Map<String, List<AvatarDto>>> getAvatars() {

        List<Avatar> avatars = avatarService.findAll();
        List<AvatarDto> avatarDtos = avatars.stream().map(avatar -> new AvatarDto(avatar.getId(), avatar.getImageUrl(), avatar.getName())).toList();
        return ResponseEntity.ok().body(Map.of("avatars", avatarDtos));
    }

    @GetMapping("/metadata/bulk")
    // format /api/user/metadata/bulk?ids=1&ids=2&ids=3s
    public ResponseEntity<Map<String, List<Avatar>>> getUsersWithMetadata(@RequestParam List<String> ids) {
        List<Avatar> avatars = avatarService.getAvatarsById(ids);

        return ResponseEntity.ok().body(Map.of("avatars", avatars));
    }

}