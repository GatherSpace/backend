package com.hari.gatherspace.service;

import com.hari.gatherspace.model.Map;
import com.hari.gatherspace.model.Space;
import com.hari.gatherspace.model.SpaceElements;
import com.hari.gatherspace.repository.SpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        space1.setElements(map.getMapElements().stream().map(e -> {
            SpaceElements spaceElements = new SpaceElements();
            spaceElements.setSpaceId(space1.getId());
            spaceElements.setElementId(e.getId());
            spaceElements.setX(e.getX());
            spaceElements.setY(e.getY());
            return spaceElementsService.save(spaceElements);
        }).toList());

        return spaceRepository.save(space1);



    }

    public Space getSpaceById(String id) {
        return spaceRepository.findById(id).orElse(null);

    }

    @Transactional
    public void deleteSpace(String id) {
        spaceRepository.deleteById(id);
    }

    public List<Space> getAllSpaces(String userId) {
        return spaceRepository.findAllByUserId(userId);
    }
}
