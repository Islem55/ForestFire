package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.ZoneEntity;
import PFE.project.ForestFire.interfaces.ZoneInterface;
import PFE.project.ForestFire.repository.AffectationRepo;
import PFE.project.ForestFire.repository.ZoneRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZoneImplement implements ZoneInterface {

    private final ZoneRepo zoneRepo;
    private final AffectationRepo affectationRepo;
    public ZoneImplement(ZoneRepo zoneRepo, AffectationRepo affectationRepo) {
        this.zoneRepo = zoneRepo;
        this.affectationRepo = affectationRepo;
    }
    @Override
    public ZoneEntity saveZone(ZoneEntity zone) {
        return zoneRepo.save(zone);
    }

    @Override
    public List<ZoneEntity> getAllZones() {
        return zoneRepo.findAll();
    }

    @Override
    public ZoneEntity getZoneById(Long id) {
        return zoneRepo.findById(id).orElse(null);
    }

    @Override
    public List<ZoneEntity> getByNomZone(String nomZone) {
        return zoneRepo.findByNomZone(nomZone);
    }

    @Override
    public List<ZoneEntity> getByNomGov(String nomGov) {
        return zoneRepo.findByNomGov(nomGov);
    }

    @Override
    public List<ZoneEntity> getByNomSecteur(String nomSecteur) {
        return zoneRepo.findByNomSecteur(nomSecteur);
    }

    @Override
    public void deleteZone(Long id) {
        zoneRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteZoneByNomZone(String nomZone) {
        zoneRepo.deleteAll(zoneRepo.findByNomZone(nomZone));
    }

    // ══ Ajouter zone par délégations ══
    @Override
    @Transactional
    public List<ZoneEntity> ajouterZoneParDelegations(
            String nomZone, String nomSecteur, String nomGov,
            List<String> delegations, String couleur) {

        List<ZoneEntity> result = new ArrayList<>();

        for (String dele : delegations) {
            // ✅ Normaliser la casse pour la comparaison
            // On cherche avec LOWER() pour ignorer la casse
            if (zoneRepo.existsByNomDeleIgnoreCase(dele)) {
                throw new IllegalArgumentException(
                        "La délégation '" + dele + "' est déjà assignée à une zone.");
            }

            ZoneEntity z = new ZoneEntity();
            z.setNomZone(nomZone);
            z.setNomDele(dele);          // on garde la casse originale
            z.setNomGov(nomGov);
            z.setNomSecteur(nomSecteur);
            z.setCouleur(couleur);
            z.setTypeZone("dele");
            result.add(zoneRepo.save(z));
        }
        return result;
    }

    // ══ Modifier zone ══
    @Override
    @Transactional
    public List<ZoneEntity> modifierZone(
            String ancienNom, String nouveauNom, String nomSecteur,
            String nomGov, List<String> delegations, String couleur) {

        // Supprimer les anciennes lignes
        zoneRepo.deleteAll(zoneRepo.findByNomZone(ancienNom));

        List<ZoneEntity> result = new ArrayList<>();
        for (String dele : delegations) {
            ZoneEntity z = new ZoneEntity();
            z.setNomZone(nouveauNom);
            z.setNomDele(dele);
            z.setNomGov(nomGov);
            z.setNomSecteur(nomSecteur);
            z.setCouleur(couleur);
            z.setTypeZone("dele");
            result.add(zoneRepo.save(z));
        }
        return result;
    }

    @Override
    public List<ZoneEntity> getZonesByGestionnaire(Long gestionnaireId) {
        return zoneRepo.findByGestionnaireId(gestionnaireId);
    }

    @Override
    public List<ZoneEntity> getZonesBySecteur(String nomSecteur) {
        return zoneRepo.findByNomSecteur(nomSecteur);
    }
    @Override
    public String getSecteurByGestionnaire(Long gestionnaireId) {
        return affectationRepo.findSecteurByGestionnaireId(gestionnaireId);
    }
    @Override
    public List<ZoneEntity> getZonesByGouvernorat(String nomGov) {
        return zoneRepo.findByNomGov(nomGov);
    }
}