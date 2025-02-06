package com.hari.gatherspace.features.space;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceElementsService {

    @Autowired
    private SpaceElementsRepository spaceElementsRepository;

    public List<SpaceElements> saveAll(List<SpaceElements> spaceElementsList) {
        return spaceElementsRepository.saveAll(spaceElementsList);
    }

    public SpaceElements save(SpaceElements spaceElements) {
        return spaceElementsRepository.save(spaceElements);
    }

    public SpaceElements findById(String id) {
        return spaceElementsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(String id) {
        spaceElementsRepository.deleteById(id);
        }
}
