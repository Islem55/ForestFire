package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.SecteurEntity;

import java.util.List;

public interface SecteurInterface {

    List<SecteurEntity> getAllSecteurs();

    SecteurEntity getSecteurById(Long id);

    List<SecteurEntity> getByGovernorate(String nomGov);

    SecteurEntity saveSecteur(SecteurEntity secteur);

    void deleteSecteur (Long id);

    List<SecteurEntity> getByNomSecteur (String nomSecteu);

}
