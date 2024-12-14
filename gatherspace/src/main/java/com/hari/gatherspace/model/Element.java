package com.hari.gatherspace.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "element")
@Getter
@Setter
public class Element {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private Integer width;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private boolean staticValue;

    @Column(nullable = false)
    private String imageUrl;


    @OneToMany(mappedBy   = "element", cascade = {CascadeType.ALL})
    private List<SpaceElements> spaces;

    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
    private List<MapElements> mapElements;
}
