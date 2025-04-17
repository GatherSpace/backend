package com.hari.gatherspace.features.avatar;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateAvatarRequest {

    private String imageUrl;

    private String name;

}