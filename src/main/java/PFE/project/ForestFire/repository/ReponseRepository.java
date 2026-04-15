package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.ReponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReponseRepository extends JpaRepository<ReponseEntity, Long> {

    // Toutes les réponses d'une affectation
    List<ReponseEntity> findByAffectationId(Long affectationId);

    //  Toutes les réponses d'un forestier
    List<ReponseEntity> findByAffectationForestierIdOrderByDateReponseDesc(Long forestierId);
}