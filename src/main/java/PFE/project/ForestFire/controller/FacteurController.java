package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.entities.FacteurEntity;
import PFE.project.ForestFire.entities.TypeFacteur;
import PFE.project.ForestFire.interfaces.FacteurInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("facteurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FacteurController {

    private final FacteurInterface facteurInterface;

    @PostMapping("/Ajouter")
    public ResponseEntity<FacteurEntity> add(@RequestBody FacteurEntity facteur) {
        return ResponseEntity.ok(facteurInterface.ajouterFacteur(facteur));
    }

    @GetMapping("AfficherTout/all")
    public ResponseEntity<List<FacteurEntity>> getAll() {
        return ResponseEntity.ok(facteurInterface.getAllFacteurs());
    }

    @GetMapping("Afficher/{id}")
    public ResponseEntity<FacteurEntity> getById(@PathVariable Long id) {
        FacteurEntity f = facteurInterface.getFacteurById(id);
        return f != null ? ResponseEntity.ok(f) : ResponseEntity.notFound().build();
    }

    @GetMapping("/Rechercher/nom")
    public ResponseEntity<List<FacteurEntity>> getByNom(@RequestParam String nom) {
        return ResponseEntity.ok(facteurInterface.getFacteurByNom(nom));
    }

    @GetMapping("/filter/type/{type}")
    public ResponseEntity<List<FacteurEntity>> getByType(@PathVariable TypeFacteur type) {
        return ResponseEntity.ok(facteurInterface.getFacteurByType(type));
    }

    @PutMapping("/Modifier/{id}")
    public ResponseEntity<FacteurEntity> update(@PathVariable Long id, @RequestBody FacteurEntity facteur) {
        return ResponseEntity.ok(facteurInterface.updateFacteur(id, facteur));
    }

    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facteurInterface.deleteFacteur(id);
        return ResponseEntity.noContent().build();
    }
}