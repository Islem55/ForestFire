package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.DelegationEntity;
import PFE.project.ForestFire.entities.FacteurEntity;
import PFE.project.ForestFire.entities.TypeFacteur;
import PFE.project.ForestFire.interfaces.FacteurInterface;
import PFE.project.ForestFire.repository.FacteurRepo; // Vérifiez que le package est correct
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
// 1. Ajoute ces imports en haut du fichier :
 import PFE.project.ForestFire.DTO.FacteurDTO;
import PFE.project.ForestFire.entities.FacteurImportant;
import PFE.project.ForestFire.repository.FacteurImportantRepo;  // ou ton repo


@Service
@RequiredArgsConstructor
public class FacteurService implements FacteurInterface {

    private final FacteurRepo facteurRepo;
    // 2. Injecte le repository FacteurImportant :
 @Autowired
   private FacteurImportantRepo facteurImportantRepo;
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


    // ============================================================
// À AJOUTER dans ton FacteurServiceImpl.java
// (dans la classe qui implémente FacteurInterface)
// ============================================================



// 3. Ajoute cette méthode dans la classe :

    @Override
    public List<FacteurDTO> getAllFacteursAvecValeurs() {

        List<FacteurEntity> facteurs = facteurRepo.findAll();
        List<FacteurDTO> result = new ArrayList<>();

        for (FacteurEntity f : facteurs) {

            FacteurDTO dto = new FacteurDTO();
            dto.setId(f.getId());
            dto.setCode(f.getCode());
            dto.setNom(f.getNom());
            dto.setTypeFacteur(f.getTypeFacteur());
            dto.setUnite(f.getUnite());
            dto.setDate(f.getDate());

            // Cherche la dernière valeur extraite pour ce facteur
            // (la plus récente selon la date)
            if (f.getFacteurImportants() != null && !f.getFacteurImportants().isEmpty()) {

                FacteurImportant derniere = f.getFacteurImportants()
                        .stream()
                        .max(Comparator.comparing(fi -> fi.getDate()))
                        .orElse(null);

                if (derniere != null) {
                    dto.setFacteurImportantId(derniere.getId());
                    dto.setValeur(derniere.getValeur());
                    dto.setDateExtraction(derniere.getDate());

                    // ✅ Utilise getNom_deleg() — nom réel dans ZoneForestiereEntity
                    if (derniere.getDelegationEntity() != null) {
                        DelegationEntity zone = derniere.getDelegationEntity();
                        dto.setNomZone(
                                zone.getNomDeleg() != null
                                        ? zone.getNomDeleg()
                                        : zone.getNomGov()
                        );
                        dto.setZoneId(zone.getId()); // ✅ getId_0() = clé primaire réelle
                    }
                }
            }

            result.add(dto);
        }

        return result;
    }

// ============================================================
// AUSSI : dans FacteurDTO.java, vérifie que zoneId est Long
// et que la méthode setZoneId(Long) existe
// ============================================================
}