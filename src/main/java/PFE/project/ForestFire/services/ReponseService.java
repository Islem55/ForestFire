package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.ReponseEntity;
import PFE.project.ForestFire.interfaces.ReponseInterface;
import PFE.project.ForestFire.repository.ReponseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReponseService implements ReponseInterface {

    private final ReponseRepository reponseRepository;

    public ReponseService(ReponseRepository reponseRepository) {
        this.reponseRepository = reponseRepository;
    }

    @Override
    public ReponseEntity saveReponse(ReponseEntity reponse) {
        return reponseRepository.save(reponse);
    }

    @Override
    public ReponseEntity updateReponse(Long id, ReponseEntity reponse) {
        ReponseEntity existing = reponseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée : " + id));
        existing.setStatut(reponse.getStatut());
        existing.setGravite(reponse.getGravite());
        existing.setCommentaire(reponse.getCommentaire());
        existing.setCoordonneeGPS(reponse.getCoordonneeGPS());
        existing.setIncendieConfirme(reponse.getIncendieConfirme());
        existing.setSuperficieEstimee(reponse.getSuperficieEstimee());
        return reponseRepository.save(existing);
    }

    @Override
    public void deleteReponse(Long id) {
        if (!reponseRepository.existsById(id)) {
            throw new RuntimeException("Réponse introuvable : " + id);
        }
        reponseRepository.deleteById(id);
    }

    @Override
    public List<ReponseEntity> getAllReponses() {
        return reponseRepository.findAll();
    }

    @Override
    public ReponseEntity getById(Long id) {
        return reponseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réponse non trouvée : " + id));
    }

    @Override
    public List<ReponseEntity> getReponsesByAffectation(Long affectationId) {
        return reponseRepository.findByAffectationId(affectationId);
    }

    @Override
    public List<ReponseEntity> getReponsesByForestier(Long forestierId) {
        return reponseRepository
                .findByAffectationForestierIdOrderByDateReponseDesc(forestierId);
    }
}