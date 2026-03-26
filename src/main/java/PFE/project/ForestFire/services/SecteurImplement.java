package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.SecteurEntity;
import PFE.project.ForestFire.interfaces.SecteurInterface;
import PFE.project.ForestFire.repository.SecteurRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void deleteSecteur(Long id) {
        secteurRepo.deleteById(id);
    }

    // ✅ Supprime TOUTES les lignes du même nomSecteur
    @Override
    @Transactional
    public void deleteSecteurByNomSecteur(String nomSecteur) {
        List<SecteurEntity> toDelete =
                secteurRepo.findByNomSecteur(nomSecteur);
        secteurRepo.deleteAll(toDelete);
    }
}