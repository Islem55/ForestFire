package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.ZoneEntity;
import java.util.List;

public interface ZoneInterface {

    ZoneEntity saveZone(ZoneEntity zone);
    List<ZoneEntity> getAllZones();
    ZoneEntity getZoneById(Long id);
    List<ZoneEntity> getByNomZone(String nomZone);
    List<ZoneEntity> getByNomGov(String nomGov);
    List<ZoneEntity> getByNomSecteur(String nomSecteur);
    void deleteZone(Long id);
    void deleteZoneByNomZone(String nomZone);

    // Ajouter une zone par délégations spécifiques
    List<ZoneEntity> ajouterZoneParDelegations(
            String nomZone, String nomSecteur, String nomGov,
            List<String> delegations, String couleur);

    // Modifier une zone
    List<ZoneEntity> modifierZone(
            String ancienNom, String nouveauNom, String nomSecteur,
            String nomGov, List<String> delegations, String couleur);

    String getSecteurByGestionnaire(Long gestionnaireId);

    List<ZoneEntity> getZonesByGestionnaire(Long gestionnaireId);

    List<ZoneEntity> getZonesBySecteur(String nomSecteur);

    List<ZoneEntity> getZonesByGouvernorat(String nomGov);
}