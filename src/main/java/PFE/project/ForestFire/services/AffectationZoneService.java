package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.AffectationZoneEntity;
import PFE.project.ForestFire.interfaces.AffectationZoneInterface;
import PFE.project.ForestFire.repository.AffectationZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AffectationZoneService implements AffectationZoneInterface {

    private final AffectationZoneRepository affectationZoneRepository;

    public AffectationZoneService(AffectationZoneRepository affectationZoneRepository) {
        this.affectationZoneRepository = affectationZoneRepository;
    }

    @Override
    public AffectationZoneEntity save(AffectationZoneEntity affectation) {

        boolean existe = affectationZoneRepository
                .existsByForestier_IdAndZone_Id(
                        affectation.getForestier().getId(),
                        affectation.getZone().getId()
                );

        if (existe) {
            throw new RuntimeException("Ce forestier est déjà affecté à cette zone !");
        }

        return affectationZoneRepository.save(affectation);
    }

    @Override
    public List<AffectationZoneEntity> getAll() {
        return affectationZoneRepository.findAll();
    }

    @Override
    public AffectationZoneEntity getById(Long id) {
        return affectationZoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affectation introuvable"));
    }

    @Override
    public void delete(Long id) {
        if (!affectationZoneRepository.existsById(id)) {
            throw new RuntimeException("Affectation inexistante !");
        }
        affectationZoneRepository.deleteById(id);
    }

    @Override
    public List<AffectationZoneEntity> getByGestionnaire(Long gestionnaireId) {
        return affectationZoneRepository.findByGestionnaire_Id(gestionnaireId);
    }

    // ✅ CORRECTION ICI
    @Override
    public List<AffectationZoneEntity> getByZone(Long zoneId) {
        return affectationZoneRepository.findByZone_Id(zoneId);
    }
}