package com.hari.gatherspace.features.space;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hari.gatherspace.features.user.User;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "space")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public String getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public List<SpaceElements> getElements() {
    return elements;
  }

  public void setElements(List<SpaceElements> elements) {
    this.elements = elements;
  }
}
