package com.hari.gatherspace.features.user;

import com.hari.gatherspace.features.avatar.Avatar;
import com.hari.gatherspace.features.avatar.AvatarResponse;
import com.hari.gatherspace.features.avatar.AvatarService;
import com.hari.gatherspace.features.avatar.CreateAvatarRequest;
import com.hari.gatherspace.features.element.*;
import com.hari.gatherspace.features.map.CreateMapRequest;
import com.hari.gatherspace.features.map.Map;
import com.hari.gatherspace.features.map.MapResponse;
import com.hari.gatherspace.features.map.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final ElementService elementService;
  private final AvatarService avatarService;
  private final MapService mapService;

  @PreAuthorize("hasRole('Admin')")
  @PostMapping("/element")
  public ResponseEntity<ElementResponse> createElement(@RequestBody CreateElementRequest request) {
    Element createdElement = elementService.createElement(request);
    ElementResponse response = new ElementResponse(createdElement.getId());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/element/{elementId}")
  public ResponseEntity<String> updateElement(
      @PathVariable String elementId, @RequestBody UpdateElementRequest request) {
    log.info("control reaches here for updating element");
    try {
      elementService.updateElement(elementId, request);
      return ResponseEntity.ok("Element updated successfully");
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PreAuthorize("hasRole('Admin')")
  @PostMapping("/avatar")
  public ResponseEntity<AvatarResponse> createAvatar(@RequestBody CreateAvatarRequest request) {
    log.info("control reaches here for creating avatar");
    Avatar createdAvatar = avatarService.createAvatar(request);
    log.info(createdAvatar.getId());
    AvatarResponse response = new AvatarResponse(createdAvatar.getId());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('Admin')")
  @PostMapping("/map")
  public ResponseEntity<MapResponse> createMap(@RequestBody CreateMapRequest request) {
    Map createdMap = mapService.createMap(request);
    MapResponse response = new MapResponse(createdMap.getId());
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
