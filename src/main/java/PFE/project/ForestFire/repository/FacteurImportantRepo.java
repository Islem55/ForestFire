package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.FacteurImportant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacteurImportantRepo extends JpaRepository<FacteurImportant, Long> {

    // la clé primaire de ZoneForestiereEntity s'appelle "id_0" pas "id"
    //    Spring Data JPA ne supporte pas les underscores dans les noms de champs via convention
    //    → on utilise @Query JPQL pour contourner ce problème
    @Query("SELECT fi FROM FacteurImportant fi WHERE fi.delegationEntity.id = :zoneId")
    List<FacteurImportant> findByZoneId(@Param("zoneId") Long zoneId);


    // ⚠️ Cette méthode doit exister exactement
    @Query("SELECT fi FROM FacteurImportant fi " +
            "WHERE fi.facteurEntity.id = :facteurId " +
            "ORDER BY fi.date DESC")
    List<FacteurImportant> findLatestByFacteurId(
            @Param("facteurId") Long facteurId
    );
    //  même problème pour findByFacteurEntity_Id (celui-là marche car  id est standard)
    List<FacteurImportant> findByFacteurEntity_Id(Long facteurId);
}