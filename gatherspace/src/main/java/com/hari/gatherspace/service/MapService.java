package com.hari.gatherspace.service;

import com.hari.gatherspace.model.Map;
import com.hari.gatherspace.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {

    @Autowired
    private MapRepository mapRepository;

    public Map getMapById(String id) {
        return mapRepository.findById(id).orElse(null);
    }
}
