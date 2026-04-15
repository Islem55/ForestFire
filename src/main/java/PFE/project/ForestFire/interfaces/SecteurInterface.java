package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.SecteurEntity;

import java.util.List;

public interface SecteurInterface {

    SecteurEntity saveSecteur(SecteurEntity secteur);
    List<SecteurEntity> getAllSecteurs();
    SecteurEntity getSecteurById(Long id);
    List<SecteurEntity> getByNomSecteur(String nomSecteur);
    List<SecteurEntity> getByGovernorate(String nomGov);
    List<SecteurEntity> getByDelegation(String nomDele);
    void deleteSecteur(Long id);
    void deleteSecteurByNomSecteur(String nomSecteur);
    String getNomGovByIdDeux(Long id2);
    List<SecteurEntity> getSecteursByGestionnaire(Long gestionnaireId);
    void updateCouleurSecteur(String nomSecteur, String couleur);

    // ✅ Nouvelle méthode modification sans supprimer/réinsérer
    List<SecteurEntity> modifierSecteur(
            String ancienNomSecteur,
            String nouveauNomSecteur,
            List<String> nouveauxGouvernorats,
            String description,
            String couleur);
}