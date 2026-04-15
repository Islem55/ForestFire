package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.DelegationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface DelegationRepo extends JpaRepository<DelegationEntity, Long> {

    // Tous les gouvernorats uniques triés
    @Query("SELECT DISTINCT d.nomGov FROM DelegationEntity d " +
            "WHERE d.nomGov IS NOT NULL ORDER BY d.nomGov")
    List<String> findDistinctNomGov();

    // Toutes les délégations d'un gouvernorat
    List<DelegationEntity> findByNomGov(String nomGov);

    // Toutes les délégations
    List<DelegationEntity> findAll();
    // ✅ @Query pour éviter le problème de parsing
    @Query("SELECT d FROM DelegationEntity d WHERE d.secteur.id = :secteurId")


    Optional<DelegationEntity> findById(Long id);



    // ✅ Délégations d'un secteur spécifique
    List<DelegationEntity> findBySecteur_Id(Long secteurId);

    // ✅ Gouvernorats uniques
    @Query("SELECT DISTINCT d.nomGov FROM DelegationEntity d " +
            "WHERE d.nomGov IS NOT NULL ORDER BY d.nomGov")
    List<String> findDistinctGouvernorats();




    // ✅ Gouvernorats uniques d'un secteur donné
    @Query("SELECT DISTINCT d.nomGov FROM DelegationEntity d " +
            "WHERE d.secteur.id = :secteurId " +
            "AND d.nomGov IS NOT NULL " +
            "ORDER BY d.nomGov")
    List<String> findGouvernoratsBySecteurId(@Param("secteurId") Long secteurId);

    // ✅ Délégations d'un secteur
    @Query("SELECT d FROM DelegationEntity d WHERE d.secteur.id = :secteurId")
    List<DelegationEntity> findBySecteurId(@Param("secteurId") Long secteurId);




    // ✅ Chercher directement par Nom_gov dans delegation
    @Query(value = "SELECT d.* FROM delegation d " +
            "WHERE TRIM(d.\"Nom_gov\") = TRIM(:nomGov) " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<DelegationEntity> findFirstByNomGov(@Param("nomGov") String nomGov);


    @Query(value = "SELECT d.* FROM delegation d " +
            "JOIN secteur s ON CAST(d.gov_id AS integer) = s.id_2 " +
            "WHERE TRIM(s.nom_gov) = TRIM(:nomGov)",
            nativeQuery = true)
    List<DelegationEntity> findAllByNomGov(@Param("nomGov") String nomGov);

    @Query(value = "SELECT d.* FROM delegation d " +
            "JOIN secteur s ON CAST(d.gov_id AS integer) = s.id_2 " +
            "WHERE TRIM(d.\"Nom_gov\") = TRIM(:nomGov) " +
            "AND s.nom_secteur IN (" +
            "  SELECT DISTINCT s2.nom_secteur FROM secteur s2 " +
            "  WHERE s2.gestionnaire_id = :gestionnaireId" +
            ") LIMIT 1",
            nativeQuery = true)
    Optional<DelegationEntity> findFirstByNomGovAndGestionnaire(
            @Param("nomGov") String nomGov,
            @Param("gestionnaireId") Long gestionnaireId);





}