package com.hari.gatherspace.service;

import com.hari.gatherspace.dto.CreateMapRequest;
import com.hari.gatherspace.dto.DefaultElement;
import com.hari.gatherspace.model.Map;
import com.hari.gatherspace.model.MapElements;
import com.hari.gatherspace.repository.MapElementRepository;
import com.hari.gatherspace.repository.MapRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapElementRepository mapElementsRepository;

    public Map getMapById(String id) {
        return mapRepository.findById(id).orElse(null);
    }


    @Transactional
    public Map createMap(CreateMapRequest request) {
        Map map = new Map();
        map.setThumbnail(request.getThumbnail());
        map.setName(request.getName());

        String[] dimensions = request.getDimensions().split("x");
        map.setWidth(Integer.parseInt(dimensions[0]));
        map.setHeight(Integer.parseInt(dimensions[1]));

        // Save the map first to get the ID
        map = mapRepository.save(map);

        // Create and associate MapElements
        List<MapElements> mapElements = new ArrayList<>();
        for (DefaultElement defaultElement : request.getDefaultElements()) {
            MapElements mapElement = new MapElements();
            mapElement.setMapId(map.getId());
            mapElement.setElementId(defaultElement.getElementId());
            mapElement.setX(defaultElement.getX());
            mapElement.setY(defaultElement.getY());
            mapElements.add(mapElement);
        }
        mapElementsRepository.saveAll(mapElements);

        return map;
    }
}