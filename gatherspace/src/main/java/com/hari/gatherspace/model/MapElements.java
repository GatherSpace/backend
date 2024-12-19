package com.hari.gatherspace.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


// admins can create map elements and assign them to a map
@Entity
@Table(name = "map_elements")
public class MapElements {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String mapId;

    @Column(nullable = false)
    private Boolean staticValue;

    @Column(nullable = false)
    private String elementId;

    private Integer x;

    private Integer y;

    @ManyToOne
    @JoinColumn(name= "mapId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private Map map;

    @ManyToOne
    @JoinColumn(name = "elementId",referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private Element element;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public Boolean getStaticValue() {
        return staticValue;
    }

    public void setStaticValue(Boolean staticValue) {
        this.staticValue = staticValue;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
