package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.FacteurEntity;
import PFE.project.ForestFire.entities.TypeFacteur;
import PFE.project.ForestFire.interfaces.FacteurInterface;
import PFE.project.ForestFire.repository.FacteurRepo; // Vérifiez que le package est correct
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacteurService implements FacteurInterface {

    private final FacteurRepo facteurRepo;

    @Override
    public FacteurEntity ajouterFacteur(FacteurEntity facteur) {
        return facteurRepo.save(facteur);
    }

    @Override
    public List<FacteurEntity> getAllFacteurs() {
        return facteurRepo.findAll();
    }

    @Override
    public FacteurEntity getFacteurById(Long id) {
        return facteurRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteFacteur(Long id) {
        facteurRepo.deleteById(id);
    }

    @Override
    public FacteurEntity updateFacteur(Long id, FacteurEntity facteur) {
        FacteurEntity f = facteurRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Facteur non trouvé avec l'id : " + id));

        f.setNom(facteur.getNom());
        f.setCode(facteur.getCode());
        f.setTypeFacteur(facteur.getTypeFacteur());

        return facteurRepo.save(f);
    }

    @Override
    public List<FacteurEntity> getFacteurByNom(String nom) {
        return facteurRepo.findByNom(nom);
    }

    @Override
    public List<FacteurEntity> getFacteurByType(TypeFacteur typeFacteur) {
        return facteurRepo.findByTypeFacteur(typeFacteur);
    }

    @Override
    public FacteurEntity getFacteurByCode(String code){
        return  facteurRepo.findByCode(code);
    }
}