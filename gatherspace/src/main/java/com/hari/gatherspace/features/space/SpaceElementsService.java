package com.hari.gatherspace.features.space;

import jakarta.transaction.Transactional;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceElementsService {

  private final SpaceElementsRepository spaceElementsRepository;

  public List<SpaceElements> saveAll(List<SpaceElements> spaceElementsList) {
    return spaceElementsRepository.saveAll(spaceElementsList);
  }

  public SpaceElements save(SpaceElements spaceElements) {
    return spaceElementsRepository.save(spaceElements);
  }

  public SpaceElements findById(String id) {
    return spaceElementsRepository.findById(id).orElse(null);
  }

  @Transactional
  public void deleteById(String id) {
    spaceElementsRepository.deleteById(id);
  }
}
