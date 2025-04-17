package com.hari.gatherspace.features.avatar;

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
@Table(name = "avatar")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Avatar {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  private String imageUrl;

  private String name;

  @OneToMany(mappedBy = "avatar", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<User> users;
}
