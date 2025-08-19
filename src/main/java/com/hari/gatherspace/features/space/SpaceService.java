package com.hari.gatherspace.features.space;

import com.hari.gatherspace.features.element.ElementDTO;
import com.hari.gatherspace.features.map.Map;
import com.hari.gatherspace.features.map.MapElements;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SpaceService {
  SpaceRepository spaceRepository;
  SpaceElementsService spaceElementsService;

  @Transactional
  public Space saveSpace(Space space) {
    return spaceRepository.save(space);
  }

  @Transactional
  public Space createSpace(Space space, Map map) {
    Space space1 = spaceRepository.save(space);
    space1.setThumbnail(map.getThumbnail());
    log.info("Creating space: spaceId={}", space1.getId());
    try {
      List<MapElements> mapElements = map.getMapElements();
      if (mapElements != null) {
        List<SpaceElements> spaceElementsList =
            mapElements.stream()
                .filter(Objects::nonNull)
                .map(
                    e -> {
                      SpaceElements spaceElements = new SpaceElements();
                      spaceElements.setSpaceId(space1.getId());

                      if (e.getElementId() != null) {
                        spaceElements.setElementId(e.getElementId());
                      } else {
                        log.warn("MapElement has a null ElementId for spaceId={}", space1.getId());
                      }

                      spaceElements.setX(e.getX());
                      spaceElements.setY(e.getY());
                      log.debug("SpaceElement prepared: elementId={}, x={}, y={}",
                          spaceElements.getElementId(), spaceElements.getX(), spaceElements.getY());
                      return spaceElements;
                    })
                .collect(Collectors.toList()); // Use collect(Collectors.toList())

        // Save SpaceElements explicitly
        spaceElementsService.saveAll(spaceElementsList);

        // Now set the saved elements to space1
        space1.setElements(spaceElementsList);
      } else {
        log.warn("mapElements is null for mapId={}", map.getId());
      }
    } catch (Exception e) {
      log.error("Exception in createSpace: {}", e.getMessage(), e);
    }
    log.info("Space Id: {}", space1.getId());
    log.info("Obj: {}", space1);
    Space savedSpace = spaceRepository.save(space1);
    log.info("Space created: spaceId={}", savedSpace.getId());
    return savedSpace;
  }

  public SpaceElements getSpaceElementById(String spaceElementId) {
    return spaceElementsService.findById(spaceElementId);
  }

  @Transactional
  public void deleteSpaceElement(String spaceElementId) {
    spaceElementsService.deleteById(spaceElementId);
  }

  public Space getSpaceById(String id) {
    Space space = spaceRepository.findById(id).orElse(null);
    return space;
  }

  @Transactional
  public SpaceElements addElement(ElementDTO element) {
    Space space = spaceRepository.findById(element.getSpaceId()).orElse(null);
    if (space == null) {
      return null;
    }
    SpaceElements spaceElements = new SpaceElements();
    spaceElements.setSpaceId(element.getSpaceId());
    spaceElements.setElementId(element.getElementId());
    spaceElements.setX(element.getX());
    spaceElements.setY(element.getY());
    SpaceElements savedSpaceElement = spaceElementsService.save(spaceElements);
    List<SpaceElements> elements = space.getElements();
    log.debug("elements size before add: {}", elements.size());
    elements.add(spaceElements);
    log.debug("elements size after add: {}", elements.size());
    space.setElements(elements);
    spaceRepository.save(space);
    log.debug("elements size persisted: {}", space.getElements().size());
    return savedSpaceElement;
  }

  @Transactional
  public void deleteSpace(String id) {
    spaceRepository.deleteById(id);
  }

  public List<Space> getAllSpaces(String userId) {
    return spaceRepository.findAllByCreatorId(userId);
  }
}
