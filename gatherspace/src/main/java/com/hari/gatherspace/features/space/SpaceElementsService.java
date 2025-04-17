package com.hari.gatherspace.features.space;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SpaceElementsService {

  private SpaceElementsRepository spaceElementsRepository;

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
