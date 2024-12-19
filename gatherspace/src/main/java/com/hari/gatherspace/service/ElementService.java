package com.hari.gatherspace.service;

import com.hari.gatherspace.dto.CreateElementRequest;
import com.hari.gatherspace.dto.UpdateElementRequest;
import com.hari.gatherspace.model.Element;
import com.hari.gatherspace.repository.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementService {

    @Autowired
    private ElementRepository elementRepository;

    public Element getElement(String elementId) {
        return elementRepository.findById(elementId).get();
    }

    public List<Element> getElements() {
        return elementRepository.findAll();
    }

    public Element createElement(CreateElementRequest request) {
        Element element = new Element();
        element.setImageUrl(request.getImageUrl());
        element.setWidth(request.getWidth());
        element.setHeight(request.getHeight());
        element.setStaticValue(request.getStaticValue());
        return elementRepository.save(element);
    }

    public void updateElement(String elementId, UpdateElementRequest request) throws Exception {
        Element element = elementRepository.findById(elementId)
                .orElseThrow(() -> new Exception("Element not found with id: " + elementId));

        element.setImageUrl(request.getImageUrl());
        // You might want to add checks here to prevent updating dimensions if required.
        elementRepository.save(element);
    }
}
