package com.hari.gatherspace.service;

import com.hari.gatherspace.model.MapElements;
import com.hari.gatherspace.repository.MapElementRepository;
import com.hari.gatherspace.repository.MapElementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapElementsService {

    @Autowired
    private MapElementRepository mapElementsRepository;

    // Add methods for creating, updating, deleting MapElements if needed
}