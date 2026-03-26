package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.AffectationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AffectationRepo extends JpaRepository<AffectationEntity, Long> {

    // Optionnel : Trouver toutes les missions d'un forestier spécifique
    List<AffectationEntity> findByForestierId(Long forestierId);

    // Optionnel : Trouver les actions pour un secteur précis
    List<AffectationEntity> findBySecteurId(Long secteurId);

}