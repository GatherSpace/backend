package com.hari.gatherspace.features.element;

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

    public List<ElementAllDto> getElements() {
        List<Element> elements =  elementRepository.findAll();

        return elements.stream().map(element -> {
            ElementAllDto dto = new ElementAllDto();
            dto.setId(element.getId());
            dto.setImageUrl(element.getImageUrl());
            dto.setWidth(element.getWidth());
            dto.setHeight(element.getHeight());
            dto.setStaticValue(element.isStaticValue());
            return dto;
        }).toList();
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
