package com.hari.gatherspace.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "element")

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
    @JsonManagedReference
    private List<SpaceElements> spaces;

    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MapElements> mapElements;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean isStaticValue() {
        return staticValue;
    }

    public void setStaticValue(boolean staticValue) {
        this.staticValue = staticValue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<SpaceElements> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<SpaceElements> spaces) {
        this.spaces = spaces;
    }

    public List<MapElements> getMapElements() {
        return mapElements;
    }

    public void setMapElements(List<MapElements> mapElements) {
        this.mapElements = mapElements;
    }
}
