package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.SecteurEntity;
import PFE.project.ForestFire.interfaces.SecteurInterface;
import PFE.project.ForestFire.repository.SecteurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecteurImplement implements SecteurInterface {

    private final SecteurRepo secteurRepo;

    @Override
    public SecteurEntity saveSecteur(SecteurEntity secteur) {
        return secteurRepo.save(secteur);
    }

    @Override
    public List<SecteurEntity> getAllSecteurs() {
        return secteurRepo.findAll();
    }

    @Override
    public SecteurEntity getSecteurById(Long id) {
        return secteurRepo.findById(id).orElse(null);
    }

    @Override
    public List<SecteurEntity> getByNomSecteur(String nomSecteur) {
        return secteurRepo.findByNomSecteur(nomSecteur);
    }

    @Override
    public List<SecteurEntity> getByGovernorate(String nomGov) {
        return secteurRepo.findByNomGov(nomGov);
    }

    @Override
    public List<SecteurEntity> getByDelegation(String nomDele) {
        return secteurRepo.findByNomDele(nomDele);
    }

    @Override
    public void deleteSecteur(Long id) {
        secteurRepo.deleteById(id);
    }

    // ✅ Suppression complète avec gestion FK
    @Override
    @Transactional
    public void deleteSecteurByNomSecteur(String nomSecteur) {
        List<SecteurEntity> toDelete = secteurRepo.findByNomSecteur(nomSecteur);
        toDelete.forEach(s -> {
            if (s.getAffectations() != null) s.getAffectations().clear();
            s.setGestionnaire(null);
        });
        secteurRepo.saveAll(toDelete);
        secteurRepo.flush();
        secteurRepo.deleteAll(toDelete);
    }

    // ✅ MODIFICATION — UPDATE direct sans supprimer/réinsérer
    @Override
    @Transactional
    public List<SecteurEntity> modifierSecteur(
            String ancienNomSecteur,
            String nouveauNomSecteur,
            List<String> nouveauxGouvernorats,
            String description,
            String couleur) {

        List<SecteurEntity> existantes =
                secteurRepo.findByNomSecteur(ancienNomSecteur);

        if (existantes.isEmpty()) return new ArrayList<>();

        List<SecteurEntity> result = new ArrayList<>();

        // ✅ Cas 1 — même nombre de gouvernorats → UPDATE ligne par ligne
        if (existantes.size() == nouveauxGouvernorats.size()) {
            for (int i = 0; i < existantes.size(); i++) {
                SecteurEntity s = existantes.get(i);
                s.setNomSecteur(nouveauNomSecteur);
                s.setNomGov(nouveauxGouvernorats.get(i));
                s.setDescription(description);
                s.setCouleur(couleur);
                result.add(secteurRepo.save(s));
            }
        }
        // ✅ Cas 2 — plus de gouvernorats → UPDATE les existants + INSERT les nouveaux
        else if (nouveauxGouvernorats.size() > existantes.size()) {
            for (int i = 0; i < existantes.size(); i++) {
                SecteurEntity s = existantes.get(i);
                s.setNomSecteur(nouveauNomSecteur);
                s.setNomGov(nouveauxGouvernorats.get(i));
                s.setDescription(description);
                s.setCouleur(couleur);
                result.add(secteurRepo.save(s));
            }
            // Insérer les gouvernorats supplémentaires
            for (int i = existantes.size(); i < nouveauxGouvernorats.size(); i++) {
                SecteurEntity nouveau = new SecteurEntity();
                nouveau.setNomSecteur(nouveauNomSecteur);
                nouveau.setNomGov(nouveauxGouvernorats.get(i));
                nouveau.setDescription(description);
                nouveau.setCouleur(couleur);
                result.add(secteurRepo.save(nouveau));
            }
        }
        // ✅ Cas 3 — moins de gouvernorats → UPDATE les premiers + DELETE les extras
        else {
            for (int i = 0; i < nouveauxGouvernorats.size(); i++) {
                SecteurEntity s = existantes.get(i);
                s.setNomSecteur(nouveauNomSecteur);
                s.setNomGov(nouveauxGouvernorats.get(i));
                s.setDescription(description);
                s.setCouleur(couleur);
                result.add(secteurRepo.save(s));
            }
            // Supprimer les lignes en trop
            for (int i = nouveauxGouvernorats.size(); i < existantes.size(); i++) {
                SecteurEntity s = existantes.get(i);
                if (s.getAffectations() != null) s.getAffectations().clear();
                s.setGestionnaire(null);
                secteurRepo.save(s);
                secteurRepo.flush();
                secteurRepo.delete(s);
            }
        }

        return result;
    }

    @Override
    public String getNomGovByIdDeux(Long id2) {
        return secteurRepo.findNomGovByIdDeux(id2);
    }

    @Override
    public List<SecteurEntity> getSecteursByGestionnaire(Long gestionnaireId) {
        return secteurRepo.findAllBySecteurOfGestionnaire(gestionnaireId);
    }

    @Override
    @Transactional
    public void updateCouleurSecteur(String nomSecteur, String couleur) {
        List<SecteurEntity> lignes = secteurRepo.findByNomSecteur(nomSecteur);
        lignes.forEach(s -> s.setCouleur(couleur));
        secteurRepo.saveAll(lignes);
    }

}