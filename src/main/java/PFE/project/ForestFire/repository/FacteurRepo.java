package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.FacteurEntity;
import PFE.project.ForestFire.entities.TypeFacteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacteurRepo extends JpaRepository<FacteurEntity, Long> {
    FacteurEntity findByCode(String code);

    // Recherche par nom (Ignorer la casse et recherche partielle)
    List<FacteurEntity> findByNom(String nom);

    // Recherche exacte par type (TOPOGRAPHIQUE, BIOCLIMATIQUE, etc.)
    List<FacteurEntity> findByTypeFacteur(TypeFacteur typeFacteur);


}
