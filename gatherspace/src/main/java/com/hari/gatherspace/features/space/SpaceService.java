package com.hari.gatherspace.features.space;

import com.hari.gatherspace.features.element.ElementDTO;
import com.hari.gatherspace.features.map.Map;
import com.hari.gatherspace.features.map.MapElements;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SpaceService {
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    SpaceElementsService spaceElementsService;

    @Transactional
    public Space saveSpace(Space space) {
        return spaceRepository.save(space);
    }

    @Transactional
    public Space createSpace(Space space, Map map) {
        Space space1 = spaceRepository.save(space);
        space1.setThumbnail(map.getThumbnail());
        System.out.println("space Id: " + " " + space1.getId());
        try {
            List<MapElements> mapElements = map.getMapElements();
            if (mapElements != null) {
                List<SpaceElements> spaceElementsList = mapElements.stream()
                        .filter(Objects::nonNull)
                        .map(e -> {
                            SpaceElements spaceElements = new SpaceElements();
                            spaceElements.setSpaceId(space1.getId());

                            if (e.getElementId() != null) {
                                spaceElements.setElementId(e.getElementId());
                            } else {
                                System.err.println("Warning: MapElement has a null ElementId.");
                            }

                            spaceElements.setX(e.getX());
                            spaceElements.setY(e.getY());
                            System.out.println("spaceElements: " + spaceElements.getElementId() + " created");
                            return spaceElements;
                        })
                        .collect(Collectors.toList()); // Use collect(Collectors.toList())

                // Save SpaceElements explicitly
                spaceElementsService.saveAll(spaceElementsList);

                // Now set the saved elements to space1
                space1.setElements(spaceElementsList);
            } else {
                System.out.println("Warning: mapElements is null for map ID: " + map.getId());
            }
        } catch (Exception e) {
            System.out.println("Exception in createSpace: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println(space1.getElements().size());
        Space savedSpace = spaceRepository.save(space1);
        System.out.println("savedSpace: " + " " + "created");
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
        Space space  =  spaceRepository.findById(id).orElse(null);
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
        System.out.println("elements size: " + elements.size());
        elements.add(spaceElements);
        System.out.println("elements size: " + elements.size());
        space.setElements(elements);
        spaceRepository.save(space);
        System.out.println("elements size: " + space.getElements().size());
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
