package com.hari.gatherspace.controller;

import com.hari.gatherspace.dto.*;
import com.hari.gatherspace.model.Avatar;
import com.hari.gatherspace.model.Element;
import com.hari.gatherspace.model.Map;
import com.hari.gatherspace.service.AvatarService;
import com.hari.gatherspace.service.ElementService;
import com.hari.gatherspace.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ElementService elementService;
    @Autowired
    private AvatarService avatarService;

    @Autowired
    private MapService mapService;

    @PostMapping("/element")
    public ResponseEntity<ElementResponse> createElement(@RequestBody CreateElementRequest request) {
        Element createdElement = elementService.createElement(request);
        ElementResponse response = new ElementResponse(createdElement.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/element/{elementId}")
    public ResponseEntity<String> updateElement(@PathVariable String elementId,  @RequestBody UpdateElementRequest request) {
        try {
            elementService.updateElement(elementId, request);
            return ResponseEntity.ok("Element updated successfully");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/avatar")
    public ResponseEntity<AvatarResponse> createAvatar(@RequestBody CreateAvatarRequest request) {
        Avatar createdAvatar = avatarService.createAvatar(request);
        AvatarResponse response = new AvatarResponse(createdAvatar.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/map")
    public ResponseEntity<MapResponse> createMap(@RequestBody CreateMapRequest request) {
        Map createdMap = mapService.createMap(request);
        MapResponse response = new MapResponse(createdMap.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}