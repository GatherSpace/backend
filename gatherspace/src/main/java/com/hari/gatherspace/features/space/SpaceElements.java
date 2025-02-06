package com.hari.gatherspace.features.space;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hari.gatherspace.features.element.Element;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;


// user can create elements in a space or can use map to assign to spaceelements

@Entity
@Table(name = "space_elements")

public class SpaceElements {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = false)
    private String elementId;

    @Column(nullable = false )
    private String spaceId;

    @Column(nullable = false)
    private Integer x;

    @Column(nullable = false)
    private Integer y;

    @ManyToOne
    @JoinColumn(name = "spaceId",referencedColumnName = "id", insertable = false, updatable = false)
    @JsonBackReference
    private Space space;

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

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
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

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
