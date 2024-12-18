package com.hari.gatherspace.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateMapRequest {

    private String thumbnail;


    private String dimensions;


    private String name;


    private List<DefaultElement> defaultElements;
}