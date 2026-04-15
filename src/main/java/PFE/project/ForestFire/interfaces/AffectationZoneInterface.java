package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.AffectationZoneEntity;
import java.util.List;

public interface AffectationZoneInterface {

    AffectationZoneEntity save(AffectationZoneEntity affectation);

    List<AffectationZoneEntity> getAll();

    AffectationZoneEntity getById(Long id);

    void delete(Long id);

    // ✅ gestionnaire → zones
    List<AffectationZoneEntity> getByGestionnaire(Long gestionnaireId);

    // ✅ zone → forestiers
    List<AffectationZoneEntity> getByZone(Long zoneId);
}