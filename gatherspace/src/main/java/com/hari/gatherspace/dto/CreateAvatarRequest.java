package com.hari.gatherspace.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAvatarRequest {

    private String imageUrl;

    private String name;
}