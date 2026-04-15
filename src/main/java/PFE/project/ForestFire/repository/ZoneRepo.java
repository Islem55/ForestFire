package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.ZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepo extends JpaRepository<ZoneEntity, Long> {

    List<ZoneEntity> findByNomZone(String nomZone);
    List<ZoneEntity> findByNomGov(String nomGov);
   // List<ZoneEntity> findByNomSecteur(String nomSecteur);
    @Query("SELECT z FROM ZoneEntity z WHERE z.nomSecteur = :nomSecteur")

    List<ZoneEntity> findByNomSecteur(@Param("nomSecteur") String nomSecteur);
    // ✅ Vérifier existence en ignorant la casse (résout le problème Nom_dele vs nom_dele)
    @Query("SELECT COUNT(z) > 0 FROM ZoneEntity z WHERE LOWER(z.nomDele) = LOWER(:nomDele)")
    boolean existsByNomDeleIgnoreCase(@Param("nomDele") String nomDele);

    // ✅ Toutes les délégations utilisées (en minuscule pour comparaison uniforme)
    @Query("SELECT DISTINCT LOWER(z.nomDele) FROM ZoneEntity z WHERE z.nomDele IS NOT NULL")
    List<String> findAllUsedDelegations();

    // ✅ Délégations utilisées par d'autres zones (ignore casse)
    @Query("SELECT DISTINCT LOWER(z.nomDele) FROM ZoneEntity z " +
            "WHERE LOWER(z.nomZone) != LOWER(:nomZone) AND z.nomDele IS NOT NULL")
    List<String> findDelegationsUsedByOtherZones(@Param("nomZone") String nomZone);

    // Gouvernorats d'un secteur (gardé pour compatibilité)
    @Query("SELECT DISTINCT z.nomGov FROM ZoneEntity z " +
            "WHERE z.nomSecteur = :nomSecteur AND z.nomGov IS NOT NULL")
    List<String> findGouvernoratsBySecteur(@Param("nomSecteur") String nomSecteur);


    // Zones d’un gestionnaire
    List<ZoneEntity> findByGestionnaireId(Long gestionnaireId);

    @Query("SELECT a.secteur.nomSecteur FROM AffectationEntity a WHERE a.gestionnaire.id = :id")
    String findSecteurByGestionnaireId(@Param("id") Long id);
}