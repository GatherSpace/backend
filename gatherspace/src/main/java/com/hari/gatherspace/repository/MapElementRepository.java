package com.hari.gatherspace.repository;

import com.hari.gatherspace.model.MapElements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapElementRepository extends JpaRepository<MapElements, String> {
}
