package com.hari.gatherspace.controller;

import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.model.Space;
import com.hari.gatherspace.model.SpaceElements;
import com.hari.gatherspace.service.MapService;
import com.hari.gatherspace.service.SpaceService;
import com.hari.gatherspace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private MapService mapService;

    @Autowired
    private UserService userService;

    private JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/space")
    public ResponseEntity<Map<String, String>> createSpace(HttpServletRequest request, @RequestBody Map<String, String> spaceDetails) {
        // TODO: Implement logic to create a new space
        Space space = new Space();
        String token = jwtUtil.extractToken(request);

        String username = jwtUtil.extractUsername(token);



        if(spaceDetails.containsKey("name") && spaceDetails.containsKey("dimensions") && spaceDetails.containsKey("mapId")) {
            space.setName(spaceDetails.get("name"));
            int width = Integer.parseInt(spaceDetails.get("dimensions").split("x")[0]);
            int height = Integer.parseInt(spaceDetails.get("dimensions").split("x")[1]);
            space.setWidth(width);
            space.setHeight(height);
            space.setCreatorId(userService.getUser(username).get().getId());
            String mapId = spaceDetails.get("mapId");

            try {
                com.hari.gatherspace.model.Map map = mapService.getMapById(mapId);

                Space newSpace= spaceService.createSpace(space, map);

                return ResponseEntity.ok(Map.of("id", newSpace.getId()));

            } catch (Exception e) {
                // TODO: Handle exception and return appropriate error message
                return ResponseEntity.status(400).body(Map.of("error", "Map not found"));
            }

        }

        return ResponseEntity.status(400).body(Map.of("error", "Invalid space details"));
    }

    @DeleteMapping("/{spaceId}")
    public ResponseEntity<Map<String, String>> deleteSpace(HttpServletRequest request, @PathVariable String spaceId) {


        String token = jwtUtil.extractToken(request);

        String username = jwtUtil.extractUsername(token);

        try {
            String userId = userService.getUser(username).get().getId();
            Space space = spaceService.getSpaceById(spaceId);

            if(space.getCreatorId().equals(userId)) {
                spaceService.deleteSpace(spaceId);
                return ResponseEntity.ok(Map.of("message", "Space deleted successfully"));
            } else {
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
                }


        } catch (Exception e) {
            // TODO: Handle exception and return appropriate error message
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<Space>> > getAllSpaces(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);

        String username = jwtUtil.extractUsername(token);

        try {
            String userId = userService.getUser(username).get().getId();
            List<Space> spaces = spaceService.getAllSpaces(userId);
            return ResponseEntity.ok(Map.of("spaces", spaces));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", List.of()));
        }

    }

}
