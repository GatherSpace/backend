package com.hari.gatherspace.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "space")
@Getter
@Setter
public class Space {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer width;

    @Column(nullable = false)
    private Integer height;

    private String thumbnail;

    @Column(nullable = false)
    private String creatorId;

    @ManyToOne
    @JoinColumn(name = "creatorId", insertable = false, updatable = false)
    private User creator;

    @OneToMany(mappedBy = "space")
    private List<SpaceElements> elements;
}
