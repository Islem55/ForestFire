package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.SecteurEntity;
import java.util.List;

public interface SecteurInterface {
    SecteurEntity saveSecteur(SecteurEntity secteur);
    List<SecteurEntity> getAllSecteurs();
    SecteurEntity getSecteurById(Long id);
    List<SecteurEntity> getByNomSecteur(String nomSecteur);
    List<SecteurEntity> getByGovernorate(String nomGov);
    void deleteSecteur(Long id);
    void deleteSecteurByNomSecteur(String nomSecteur);
}