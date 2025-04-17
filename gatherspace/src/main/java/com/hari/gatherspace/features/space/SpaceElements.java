package com.hari.gatherspace.features.space;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hari.gatherspace.features.element.Element;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

// user can create elements in a space or can use map to assign to spaceelements

@Entity
@Data
@Table(name = "space_elements")
public class SpaceElements {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(nullable = false)
  private String elementId;

  @Column(nullable = false)
  private String spaceId;

  @Column(nullable = false)
  private Integer x;

  @Column(nullable = false)
  private Integer y;

  @ManyToOne
  @JoinColumn(name = "spaceId", referencedColumnName = "id", insertable = false, updatable = false)
  @JsonBackReference
  private Space space;

  @ManyToOne
  @JoinColumn(
      name = "elementId",
      referencedColumnName = "id",
      insertable = false,
      updatable = false)
  @JsonBackReference
  private Element element;
}
