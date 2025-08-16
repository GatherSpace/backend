package com.hari.gatherspace.features.space;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {

  public List<Space> findAllByCreatorId(String userId);
}
