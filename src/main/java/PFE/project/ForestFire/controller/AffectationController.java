package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.entities.AffectationEntity;
import PFE.project.ForestFire.interfaces.AffectationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("affectations")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AffectationController {

    @Autowired
    private AffectationInterface affectationInterface;


    // AFFECTER (Ajouter une action)
    @PostMapping("/ajouter")
    public ResponseEntity<AffectationEntity> saveAffectation(@RequestBody AffectationEntity affectation) {
        // L'admin envoie l'ID du forestier, l'ID du secteur et le texte de l action
        return ResponseEntity.ok(affectationInterface.saveAffectation(affectation));
    }

    // MODIFIER (Changer l'action ou le responsable)
    @PutMapping("/modifier/{id}")
    public ResponseEntity<AffectationEntity> updateAffectation(@PathVariable Long id, @RequestBody AffectationEntity details) {
        AffectationEntity s = affectationInterface.getById(id);

        s.setForestier(details.getForestier());
        s.setSecteur(details.getSecteur());
        s.setAction(details.getAction());

        return ResponseEntity.ok(affectationInterface.updateAffectation(id,s));
    }

    // SUPPRIMER (Annuler l'affectation)
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> deleteAffectation(@PathVariable Long id) {
        affectationInterface.deleteAffectation(id);
        return ResponseEntity.noContent().build();
    }

    // LISTER (Voir toutes les missions en cours)
    @GetMapping("/toutes")
    public List<AffectationEntity> getAllAffectations() {
        return affectationInterface.getAllAffectations();
    }
}