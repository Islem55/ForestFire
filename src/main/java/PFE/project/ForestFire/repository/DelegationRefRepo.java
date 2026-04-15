package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.DelegationRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelegationRefRepo extends JpaRepository<DelegationRefEntity, Long> {

    // Délégations par gouvernorat
    @Query("SELECT d.nomDele FROM DelegationRefEntity d WHERE LOWER(d.nomGov) = LOWER(:nomGov) ORDER BY d.nomDele")
    List<String> findNomDeleByNomGov(@Param("nomGov") String nomGov);

    // Gouvernorats par secteur
    @Query("SELECT DISTINCT d.nomGov FROM DelegationRefEntity d WHERE LOWER(d.nomSecteur) = LOWER(:nomSecteur) ORDER BY d.nomGov")
    List<String> findGouvernoratsBySecteur(@Param("nomSecteur") String nomSecteur);
}