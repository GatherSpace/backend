package com.hari.gatherspace.controller;

import com.hari.gatherspace.config.JwtUtil;
import com.hari.gatherspace.dto.ElementAllDto;
import com.hari.gatherspace.dto.ElementDTO;
import com.hari.gatherspace.dto.SpaceDTO;
import com.hari.gatherspace.model.Element;
import com.hari.gatherspace.model.Space;
import com.hari.gatherspace.model.SpaceElements;
import com.hari.gatherspace.service.ElementService;
import com.hari.gatherspace.service.MapService;
import com.hari.gatherspace.service.SpaceService;
import com.hari.gatherspace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ElementService elementService;

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
                System.out.println(map.getId());
                Space newSpace= spaceService.createSpace(space, map);
                System.out.println(newSpace.getId());
                return ResponseEntity.ok(Map.of("id", newSpace.getId()));

            } catch (Exception e) {
                // TODO: Handle exception and return appropriate error message
                return ResponseEntity.status(400).body(Map.of("error", "Map not found"));
            }

        }

        return ResponseEntity.status(400).body(Map.of("error", "Invalid space details"));
    }

    @DeleteMapping("/space/{spaceId}")
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

    @GetMapping("space/all")
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

    @GetMapping("space/{spaceId}")
    public ResponseEntity<SpaceDTO> getSpaceById(@PathVariable String spaceId) {
        try {
            Space space = spaceService.getSpaceById(spaceId);

            SpaceDTO spaceDTO = new SpaceDTO();
            spaceDTO.setId(space.getId());
            spaceDTO.setName(space.getName());
            spaceDTO.setCreatorId(space.getCreatorId());
            spaceDTO.setThumbnail(space.getThumbnail());
            spaceDTO.setDimensions(space.getWidth() + "x" + space.getHeight());
            spaceDTO.setElements(space.getElements());

            return ResponseEntity.ok(spaceDTO);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("space/element")
    public ResponseEntity<Map<String, String>> addElement(@RequestBody Map<String, String> elementDetails) {
        ElementDTO elementDTO = new ElementDTO();
        elementDTO.setElementId(elementDetails.get("elementId"));
        elementDTO.setSpaceId(elementDetails.get("spaceId"));
        elementDTO.setX(Integer.parseInt(elementDetails.get("x")));
        elementDTO.setY(Integer.parseInt(elementDetails.get("y")));

        try {
            SpaceElements spaceElements = spaceService.addElement(elementDTO);
            if (spaceElements != null) {
                return ResponseEntity.ok(Map.of("id", spaceElements.getId()));
            }
            return ResponseEntity.status(400).body(Map.of("Error", "Space Not Found"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", "Invalid element details"));
        }
    }

    @DeleteMapping("space/element")
    public ResponseEntity<Map<String, String>> removeElement(HttpServletRequest request, @RequestBody Map<String, String> elementDetails) {
        String token = jwtUtil.extractToken(request);
        String username = jwtUtil.extractUsername(token);

        try {
            String userId = userService.getUser(username).get().getId();
            String spaceElementId = elementDetails.get("id");

            // 1. Fetch the SpaceElement
            SpaceElements spaceElement = spaceService.getSpaceElementById(spaceElementId);

            if (spaceElement == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "SpaceElement not found"));
            }

            // 2. Fetch the associated Space
            Space space = spaceService.getSpaceById(spaceElement.getSpaceId());

            if (space == null) {
                // Handle case where Space is not found (shouldn't happen normally)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Associated Space not found"));
            }

            // 3. Check if the user is the creator of the Space
            if (!space.getCreatorId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized: Only the space creator can delete elements"));
            }

            // 4. Delete the SpaceElement
            spaceService.deleteSpaceElement(spaceElementId);

            return ResponseEntity.ok(Map.of("message", "SpaceElement deleted successfully"));

        } catch (Exception e) {
            // Log the exception for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/elements")
    public ResponseEntity<Map<String, List<ElementAllDto>>> getElements() {
        try {
            elementService.getElements();
            return ResponseEntity.ok(Map.of("elements", elementService.getElements()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", null));
        }
    }

    @GetMapping("/maps")
    public ResponseEntity<Map<String, List<com.hari.gatherspace.model.Map>>> getMaps() {
        try {
            List<com.hari.gatherspace.model.Map> maps = mapService.getAllMaps();
            return ResponseEntity.ok(Map.of("maps", maps));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", null));
        }
    }
}
