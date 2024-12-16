package com.hari.gatherspace.service;


import com.hari.gatherspace.model.SpaceElements;
import com.hari.gatherspace.repository.SpaceElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpaceElementsService {

    @Autowired
    private SpaceElementsRepository spaceElementsRepository;

    public SpaceElements save(SpaceElements spaceElements) {
        return spaceElementsRepository.save(spaceElements);
    }
}
