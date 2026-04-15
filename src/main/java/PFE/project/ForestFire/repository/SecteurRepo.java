package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.SecteurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurRepo extends JpaRepository<SecteurEntity, Long> {

    List<SecteurEntity> findByNomSecteur(String nomSecteur);

    List<SecteurEntity> findByNomGov(String nomGov);

    // ✅ Par délégation
    List<SecteurEntity> findByNomDele(String nomDele);

    @Query("SELECT s FROM SecteurEntity s WHERE s.gestionnaire.id = :id")
    List<SecteurEntity> findByGestionnaireId(@Param("id") Long id);

    @Query("SELECT s FROM SecteurEntity s WHERE s.nomSecteur IN " +
            "(SELECT DISTINCT s2.nomSecteur FROM SecteurEntity s2 " +
            "WHERE s2.gestionnaire.id = :id)")
    List<SecteurEntity> findAllBySecteurOfGestionnaire(@Param("id") Long id);

    @Query("SELECT DISTINCT s.nomSecteur FROM SecteurEntity s " +
            "WHERE s.gestionnaire.id = :id")
    List<String> findNomSecteurByGestionnaireId(@Param("id") Long id);

    @Query("SELECT s FROM SecteurEntity s WHERE s.nomSecteur IN :noms")
    List<SecteurEntity> findByNomSecteurIn(@Param("noms") List<String> noms);

    @Query("SELECT DISTINCT s.nomGov FROM SecteurEntity s WHERE s.id2 = :idDeux")
    String findNomGovByIdDeux(@Param("idDeux") Long idDeux);

    // ✅ Toutes les délégations utilisées
    @Query("SELECT DISTINCT s.nomDele FROM SecteurEntity s " +
            "WHERE s.nomDele IS NOT NULL")
    List<String> findAllUsedDelegations();

    // ✅ Délégations utilisées par d'autres secteurs
    @Query("SELECT DISTINCT s.nomDele FROM SecteurEntity s " +
            "WHERE s.nomSecteur != :nomSecteur AND s.nomDele IS NOT NULL")
    List<String> findDelegationsUsedByOtherSecteurs(
            @Param("nomSecteur") String nomSecteur);

    // Gouvernorats utilisés (gardé pour compatibilité)
    @Query("SELECT DISTINCT s.nomGov FROM SecteurEntity s " +
            "WHERE s.nomGov IS NOT NULL")
    List<String> findAllUsedGouvernorats();

    @Query("SELECT DISTINCT s.nomGov FROM SecteurEntity s " +
            "WHERE s.nomSecteur != :nomSecteur AND s.nomGov IS NOT NULL")
    List<String> findGouvernoratsUsedByOtherSecteurs(
            @Param("nomSecteur") String nomSecteur);



    @Query("SELECT DISTINCT s.nomDele FROM SecteurEntity s " +
            "WHERE s.nomGov = :nomGov AND s.nomSecteur = :nomSecteur " +
            "AND s.nomDele IS NOT NULL ORDER BY s.nomDele")
    List<String> findDistinctNomDeleByNomGovAndNomSecteur(
            @Param("nomGov") String nomGov,
            @Param("nomSecteur") String nomSecteur);

    @Query("SELECT DISTINCT s.nomDele FROM SecteurEntity s " +
            "WHERE s.nomGov = :nomGov AND s.nomDele IS NOT NULL " +
            "ORDER BY s.nomDele")
    List<String> findDelegationsByGov(@Param("nomGov") String nomGov);




}