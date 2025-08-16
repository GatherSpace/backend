package com.hari.gatherspace.features.space;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceElementsRepository extends JpaRepository<SpaceElements, String> {}
