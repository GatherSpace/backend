package com.hari.gatherspace.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


// user can create elements in a space or can use map to assign to spaceelements

@Entity
@Table(name = "space_elements")
@Getter
@Setter
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
    private Space space;

    @ManyToOne
    @JoinColumn(name = "elementId",referencedColumnName = "id", insertable = false, updatable = false)
    private Element element;
}
