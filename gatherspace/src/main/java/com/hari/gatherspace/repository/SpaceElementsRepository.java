package com.hari.gatherspace.repository;

import com.hari.gatherspace.model.SpaceElements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceElementsRepository extends JpaRepository<SpaceElements, String> {
}
