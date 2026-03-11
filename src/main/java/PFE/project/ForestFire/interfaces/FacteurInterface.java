package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.FacteurEntity;
import PFE.project.ForestFire.entities.TypeFacteur;

import java.util.List;

public interface FacteurInterface {

    FacteurEntity ajouterFacteur(FacteurEntity facteur);

    List<FacteurEntity> getAllFacteurs();

    FacteurEntity getFacteurById(Long id);

    FacteurEntity updateFacteur(Long id , FacteurEntity facteur);

    void deleteFacteur(Long id);

    FacteurEntity getFacteurByCode(String code);

    List<FacteurEntity> getFacteurByType(TypeFacteur typeFacteur) ;

    List<FacteurEntity> getFacteurByNom(String nom);
}
