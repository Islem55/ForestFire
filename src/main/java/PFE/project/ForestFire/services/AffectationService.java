package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.AffectationEntity;
import PFE.project.ForestFire.interfaces.AffectationInterface;
import PFE.project.ForestFire.repository.AffectationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffectationService implements AffectationInterface {

    private final AffectationRepo affectationRepo;

    public AffectationService(AffectationRepo affectationRepo) {
        this.affectationRepo = affectationRepo;
    }

    @Override
    public AffectationEntity saveAffectation(AffectationEntity affectation) {
        // L'admin crée l'affectation entre un forestier, un secteur et une action
        return affectationRepo.save(affectation);
    }

    @Override
    public AffectationEntity updateAffectation(Long id, AffectationEntity details) {
        AffectationEntity existing = affectationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Affectation non trouvée avec l'id : " + id));

        // Mise à jour des champs
        existing.setForestier(details.getForestier());
        existing.setSecteur(details.getSecteur());
        existing.setAction(details.getAction());

        return affectationRepo.save(existing);
    }

    @Override
    public void deleteAffectation(Long id) {
        if (!affectationRepo.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Affectation inexistante");
        }
        affectationRepo.deleteById(id);
    }

    @Override
    public List<AffectationEntity> getAllAffectations() {
        return affectationRepo.findAll();
    }

    @Override
    public AffectationEntity getById(Long id) {
        return affectationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Affectation non trouvée"));
    }
}