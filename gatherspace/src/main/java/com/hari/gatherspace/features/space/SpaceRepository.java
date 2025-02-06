package com.hari.gatherspace.features.space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {

    public List<Space> findAllByCreatorId(String userId);
}
