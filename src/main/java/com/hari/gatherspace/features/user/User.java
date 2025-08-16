package com.hari.gatherspace.features.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hari.gatherspace.features.avatar.Avatar;
import com.hari.gatherspace.features.space.Space;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  private String avatarId;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "VARCHAR(20)")
  private Role role;

  @OneToMany(mappedBy = "creator")
  @JsonManagedReference
  private List<Space> spaces;

  @ManyToOne
  @JoinColumn(name = "avatarId", insertable = false, updatable = false)
  @JsonBackReference
  private Avatar avatar;
}
