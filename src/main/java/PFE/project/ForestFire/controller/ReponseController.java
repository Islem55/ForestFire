package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.entities.ReponseEntity;
import PFE.project.ForestFire.interfaces.ReponseInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reponses")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ReponseController {

    private final ReponseInterface reponseInterface;

    public ReponseController(ReponseInterface reponseInterface) {
        this.reponseInterface = reponseInterface;
    }

    //  Ajouter une réponse
    @PostMapping("/ajouter")
    public ResponseEntity<?> saveReponse(@RequestBody ReponseEntity reponse) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reponseInterface.saveReponse(reponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //  Modifier une réponse
    @PutMapping("/modifier/{id}")
    public ResponseEntity<?> updateReponse(
            @PathVariable Long id,
            @RequestBody ReponseEntity reponse) {
        try {
            return ResponseEntity.ok(reponseInterface.updateReponse(id, reponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //  Supprimer une réponse
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<?> deleteReponse(@PathVariable Long id) {
        try {
            reponseInterface.deleteReponse(id);
            return ResponseEntity.ok(Map.of("message", "Réponse supprimée"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //  Toutes les réponses
    @GetMapping("/toutes")
    public ResponseEntity<List<ReponseEntity>> getAllReponses() {
        return ResponseEntity.ok(reponseInterface.getAllReponses());
    }

    //  Réponse par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reponseInterface.getById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //  Réponses par affectation
    @GetMapping("/affectation/{affectationId}")
    public ResponseEntity<List<ReponseEntity>> getByAffectation(
            @PathVariable Long affectationId) {
        return ResponseEntity.ok(
                reponseInterface.getReponsesByAffectation(affectationId));
    }

    //  Réponses par forestier
    @GetMapping("/forestier/{forestierId}")
    public ResponseEntity<List<ReponseEntity>> getByForestier(
            @PathVariable Long forestierId) {
        return ResponseEntity.ok(
                reponseInterface.getReponsesByForestier(forestierId));
    }
}