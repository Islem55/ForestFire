package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.FacteurImportant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacteurImportantRepo extends JpaRepository<FacteurImportant, Long> {
    // Récupérer toutes les valeurs (pente, aspect, etc.) pour une zone précise
    List<FacteurImportant> findByZoneForestiereEntity_Id(Long zoneId);

    // Trouver une valeur spécifique d'un facteur pour une zone
    List<FacteurImportant> findByFacteurEntity_Id(Long facteurId);
}