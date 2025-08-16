package com.hari.gatherspace.features.space;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hari.gatherspace.features.user.User;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "space")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
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

  // size changed to 255 to map with User Entity id as it is a foreign key
  @Column(nullable = false, length = 255)
  private String creatorId;

  @ManyToOne
  // referenceColumnName changed
  @JoinColumn(
      name = "creatorId",
      referencedColumnName = "id",
      insertable = false,
      updatable = false)
  @JsonBackReference
  private User creator;

  @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<SpaceElements> elements;
}
