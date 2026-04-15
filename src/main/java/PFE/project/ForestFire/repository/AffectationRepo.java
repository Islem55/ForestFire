package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.AffectationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AffectationRepo extends JpaRepository<AffectationEntity, Long> {

    // gestionnaire → affectations
    List<AffectationEntity> findByGestionnaireId(Long gestionnaireId);

    // secteur → affectations
    List<AffectationEntity> findBySecteurId(Long secteurId);

    // récupérer nom secteur
    @Query("SELECT a.secteur.nomSecteur FROM AffectationEntity a WHERE a.gestionnaire.id = :id")
    String findSecteurByGestionnaireId(@Param("id") Long id);

    // forestier
    List<AffectationEntity> findByForestierId(Long forestierId);
}