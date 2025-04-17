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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

  @Autowired private ElementService elementService;
  @Autowired private AvatarService avatarService;

  @Autowired private MapService mapService;

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
    System.out.println("control reaches here for updating element");
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
    System.out.println("control reaches here for creating avatar");
    Avatar createdAvatar = avatarService.createAvatar(request);
    System.out.println(createdAvatar.getId());
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
