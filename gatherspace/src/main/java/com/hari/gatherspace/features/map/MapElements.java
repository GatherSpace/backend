package com.hari.gatherspace.features.map;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hari.gatherspace.features.element.Element;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

// admins can create map elements and assign them to a map
@Entity
@Data
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
  @JoinColumn(name = "mapId", referencedColumnName = "id", insertable = false, updatable = false)
  @JsonBackReference
  private Map map;

  @ManyToOne
  @JoinColumn(
      name = "elementId",
      referencedColumnName = "id",
      insertable = false,
      updatable = false)
  @JsonBackReference
  private Element element;
}
