package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.ReponseEntity;
import java.util.List;

public interface ReponseInterface {

    //  Ajouter une réponse
    ReponseEntity saveReponse(ReponseEntity reponse);

    //  Modifier une réponse
    ReponseEntity updateReponse(Long id, ReponseEntity reponse);

    //  Supprimer une réponse
    void deleteReponse(Long id);

    //  Récupérer toutes les réponses
    List<ReponseEntity> getAllReponses();

    //  Récupérer par ID
    ReponseEntity getById(Long id);

    //  Récupérer les réponses d'une affectation
    List<ReponseEntity> getReponsesByAffectation(Long affectationId);

    //  Récupérer les réponses d'un forestier
    List<ReponseEntity> getReponsesByForestier(Long forestierId);
}