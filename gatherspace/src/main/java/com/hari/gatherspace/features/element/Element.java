package com.hari.gatherspace.features.element;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hari.gatherspace.features.map.MapElements;
import com.hari.gatherspace.features.space.SpaceElements;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "element")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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

  @OneToMany(
      mappedBy = "element",
      cascade = {CascadeType.ALL})
  @JsonManagedReference
  private List<SpaceElements> spaces;

  @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<MapElements> mapElements;
}
