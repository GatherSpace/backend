package com.hari.gatherspace.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateElementRequest {

    private String imageUrl;


    private Integer width;


    private Integer height;


    private Boolean staticValue;
}