package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.RoleName;
import PFE.project.ForestFire.entities.SecteurEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecteurRepo extends JpaRepository<SecteurEntity,Long> {
    List<SecteurEntity> findByNomGov(String nomGov);

    List<SecteurEntity> findByNomDele(String nomDele);

    List<SecteurEntity> findByNomSecteur(String nomSecteur);


}
