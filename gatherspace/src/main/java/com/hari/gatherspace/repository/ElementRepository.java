package com.hari.gatherspace.repository;

import com.hari.gatherspace.model.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementRepository extends JpaRepository<Element, String> {

}
