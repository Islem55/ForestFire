package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.AffectationZoneEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AffectationZoneRepository
        extends JpaRepository<AffectationZoneEntity, Long> {

    // ✅ gestionnaire → affectations
    List<AffectationZoneEntity> findByGestionnaire_Id(Long gestionnaireId);

    // ✅ zone → affectations
    @Query("SELECT a FROM AffectationZoneEntity a WHERE a.zone.id = :zoneId")
    List<AffectationZoneEntity> findByZoneId(@Param("zoneId") Long zoneId);

    // ✅ vérifier doublon (forestier + zone)
    @Query("SELECT COUNT(a) > 0 FROM AffectationZoneEntity a " +
            "WHERE a.forestier.id = :forestierId " +
            "AND a.zone.id = :zoneId")
    boolean existsByForestierAndZone(
            @Param("forestierId") Long forestierId,
            @Param("zoneId") Long zoneId);

    @Query("SELECT a FROM AffectationZoneEntity a WHERE a.zone.delegation.id = :delegationId")
    List<AffectationZoneEntity> findByDelegationId(@Param("delegationId") Long delegationId);




    // ✅ par zone
    List<AffectationZoneEntity> findByZone_Id(Long zoneId);

    // ✅ éviter doublon
    boolean existsByForestier_IdAndZone_Id(Long forestierId, Long zoneId);
}
