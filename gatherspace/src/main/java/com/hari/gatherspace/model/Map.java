package com.hari.gatherspace.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "maps")
@Getter
@Setter
public class Map {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private String name;

    private String thumbnail;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL)
    private List<MapElements> mapElements;
}
