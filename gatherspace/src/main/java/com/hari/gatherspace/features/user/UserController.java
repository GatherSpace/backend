package com.hari.gatherspace.features.user;

import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.features.avatar.Avatar;
import com.hari.gatherspace.features.avatar.AvatarDto;
import com.hari.gatherspace.features.avatar.AvatarService;
import com.hari.gatherspace.features.avatar.AvatarUserDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  UserService userService;

  AvatarService avatarService;
  private JwtUtil jwtUtil;

  @PostMapping("/metadata")
  public ResponseEntity<Map<String, String>> saveUserMetadata(
      HttpServletRequest request, @RequestBody Map<String, String> metadata) {
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
    List<AvatarDto> avatarDtos =
        avatars.stream()
            .map(
                avatar ->
                    new AvatarDto(
                        avatar.getId(),
                        avatar.getImageUrl(),
                        avatar.getName(),
                        avatar.getUsers().stream().map(user -> user.getId()).toList()))
            .toList();
    return ResponseEntity.ok().body(Map.of("avatars", avatarDtos));
  }

  @GetMapping("/metadata/bulk")
  // format /api/user/metadata/bulk?ids=1&ids=2&ids=3s
  public ResponseEntity<Map<String, List<AvatarUserDto>>> getUsersWithMetadata(
      @RequestParam List<String> ids) {
    System.out.println(ids.size());
    List<AvatarUserDto> avatars = avatarService.getAvatarsById(ids);

    return ResponseEntity.ok().body(Map.of("avatars", avatars));
  }
}
