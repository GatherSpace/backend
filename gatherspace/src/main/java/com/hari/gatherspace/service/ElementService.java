package com.hari.gatherspace.service;

import com.hari.gatherspace.model.Element;
import com.hari.gatherspace.repository.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementService {

    @Autowired
    private ElementRepository elementRepository;

    public List<Element> getElements() {
        return elementRepository.findAll();
    }
}
