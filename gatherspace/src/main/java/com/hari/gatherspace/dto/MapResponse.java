package com.hari.gatherspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class MapResponse {
    private String id;

    public MapResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}