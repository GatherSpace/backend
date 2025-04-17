package com.hari.gatherspace.features.map;

import com.hari.gatherspace.features.element.DefaultElement;
import com.hari.gatherspace.features.element.Element;
import com.hari.gatherspace.features.element.ElementService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MapService {

  private MapRepository mapRepository;

  private MapElementRepository mapElementsRepository;

  private ElementService elementService;

  public Map getMapById(String id) {
    return mapRepository.findById(id).orElse(null);
  }

  public List<Map> getAllMaps() {
    return mapRepository.findAll();
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
      Element element = elementService.getElement(defaultElement.getElementId());
      mapElement.setElementId(defaultElement.getElementId());
      mapElement.setMapId(map.getId());
      // if x and y are outside the map, set them to the edge of the map

      mapElement.setX(defaultElement.getX());
      mapElement.setY(defaultElement.getY());
      mapElement.setStaticValue(element.isStaticValue());
      mapElements.add(mapElement);
    }

    map.setMapElements(mapElements);

    // Save the map with the MapElements

    return mapRepository.save(map);
  }
}
