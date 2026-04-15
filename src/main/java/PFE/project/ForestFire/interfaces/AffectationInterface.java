package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.AffectationEntity;
import java.util.List;

public interface AffectationInterface {

    AffectationEntity saveAffectation(AffectationEntity affectation);

    AffectationEntity updateAffectation(Long id, AffectationEntity details);

    void deleteAffectation(Long id);

    List<AffectationEntity> getAllAffectations();
    List<AffectationEntity> getAffectationsByForestier(Long forestierId);

    AffectationEntity getById(Long id);

    // ✅ IMPORTANT : récupérer secteur du gestionnaire
    List<AffectationEntity> getByGestionnaire(Long gestionnaireId);

    String getSecteurByGestionnaire(Long gestionnaireId);
}