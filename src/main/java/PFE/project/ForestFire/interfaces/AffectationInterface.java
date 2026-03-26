package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.AffectationEntity;
import java.util.List;

public interface AffectationInterface {
    AffectationEntity saveAffectation(AffectationEntity affectation);
    AffectationEntity updateAffectation(Long id, AffectationEntity details);
    void deleteAffectation(Long id);

    List<AffectationEntity> getAllAffectations();
    AffectationEntity getById(Long id);
}