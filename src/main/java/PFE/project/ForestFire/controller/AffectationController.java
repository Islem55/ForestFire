/**package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.entities.AffectationEntity;
import PFE.project.ForestFire.interfaces.AffectationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("affectations")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
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

    //  Voir les affectations d’un forestier
    @GetMapping("/par-forestier/{id}")
    public ResponseEntity<List<AffectationEntity>> getByForestier(@PathVariable Long id) {
        return ResponseEntity.ok(affectationInterface.getAffectationsByForestier(id));
    }


}**/


package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.entities.AffectationEntity;
import PFE.project.ForestFire.interfaces.AffectationInterface;
import PFE.project.ForestFire.services.AffectationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/affectations")
@CrossOrigin(origins = "*")
public class AffectationController {

    private final AffectationService service;

    public AffectationController(AffectationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody AffectationEntity affectation) {
        return ResponseEntity.ok(service.saveAffectation(affectation));
    }

    @GetMapping
    public ResponseEntity<List<AffectationEntity>> getAll() {
        return ResponseEntity.ok(service.getAllAffectations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AffectationEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteAffectation(id);
        return ResponseEntity.ok("Deleted");
    }

    // ✅ récupérer par forestier
    @GetMapping("/forestier/{id}")
    public ResponseEntity<List<AffectationEntity>> getByForestier(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAffectationsByForestier(id));
    }
}