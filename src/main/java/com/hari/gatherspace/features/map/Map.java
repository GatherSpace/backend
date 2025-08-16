package com.hari.gatherspace.features.map;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "maps")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
  @JsonManagedReference
  private List<MapElements> mapElements;
}
