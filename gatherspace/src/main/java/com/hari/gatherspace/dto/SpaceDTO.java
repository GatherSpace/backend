package com.hari.gatherspace.dto;

import com.hari.gatherspace.model.SpaceElements;
import com.hari.gatherspace.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
public class SpaceDTO {


    private String id;


    private String dimensions;
    private String name;

    private String thumbnail;

    private String creatorId;



    private List<SpaceElements> elements;
}
