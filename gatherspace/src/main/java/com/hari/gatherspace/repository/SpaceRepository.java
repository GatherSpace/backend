package com.hari.gatherspace.repository;

import com.hari.gatherspace.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {

    public List<Space> findAllByCreatorId(String userId);
}
