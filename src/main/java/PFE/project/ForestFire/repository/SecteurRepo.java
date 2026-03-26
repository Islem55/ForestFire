package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.SecteurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurRepo extends JpaRepository<SecteurEntity, Long> {

    // Toutes les lignes d'un même nomSecteur
    List<SecteurEntity> findByNomSecteur(String nomSecteur);

    // Toutes les lignes d'un gouvernorat
    List<SecteurEntity> findByNomGov(String nomGov);

    // Tous les nomSecteur distincts
    @Query("SELECT DISTINCT s.nomSecteur FROM SecteurEntity s " +
            "WHERE s.nomSecteur IS NOT NULL ORDER BY s.nomSecteur")
    List<String> findDistinctNomSecteur();
}