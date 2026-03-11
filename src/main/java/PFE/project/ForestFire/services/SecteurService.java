package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.SecteurEntity;
import PFE.project.ForestFire.interfaces.SecteurInterface;
import PFE.project.ForestFire.repository.SecteurRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SecteurService implements SecteurInterface {
    // @Autowired est une annotation de Spring qui permet l'injection automatique des dépendances.
// Elle demande à Spring de créer l'objet nécessaire et de l'injecter dans cette classe.
// Cela évite de créer l'objet manuellement avec "new".


    private final SecteurRepo secteurRepo;

    public SecteurService(SecteurRepo secteurRepo) {
        this.secteurRepo = secteurRepo;
    }

    @Override
    public List<SecteurEntity> getAllSecteurs() {
        return secteurRepo.findAll();
    }

    @Override
    public SecteurEntity getSecteurById(Long id) {
        return secteurRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Secteur non trouvé"));
    }

    @Override
    public List<SecteurEntity> getByGovernorate(String nomGov) {
        return secteurRepo.findByNomGov(nomGov);
    }

    @Override
    public SecteurEntity saveSecteur(SecteurEntity secteur) {
        return secteurRepo.save(secteur);
    }

    @Override
    public List<SecteurEntity> getByNomSecteur(String nomSecteur) {
        return secteurRepo.findByNomSecteur(nomSecteur);
    }

    @Override
    public void deleteSecteur(Long id) {
        secteurRepo.deleteById(id);
    }
}