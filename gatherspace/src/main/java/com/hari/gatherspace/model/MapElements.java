package com.hari.gatherspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


// admins can create map elements and assign them to a map
@Entity
@Table(name = "map_elements")
@Getter
@Setter
public class MapElements {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String mapId;

    @Column(nullable = false)
    private String elementType;

    @Column(nullable = false)
    private String elementId;

    private Integer x;

    private Integer y;

    @ManyToOne
    @JoinColumn(name= "mapId", referencedColumnName = "id", insertable = false, updatable = false)
    private Map map;

    @ManyToOne
    @JoinColumn(name = "elementId",referencedColumnName = "id", insertable = false, updatable = false)
    private Element element;


}
