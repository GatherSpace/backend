package com.hari.gatherspace.repository;

import com.hari.gatherspace.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MapRepository extends JpaRepository<Map, String> {
}
