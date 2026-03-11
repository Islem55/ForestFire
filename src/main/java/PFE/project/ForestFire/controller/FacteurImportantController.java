package PFE.project.ForestFire.controller;


import PFE.project.ForestFire.DTO.FacteurDTO;
import PFE.project.ForestFire.entities.FacteurImportant;
import PFE.project.ForestFire.interfaces.FacteurImportantInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("facteurs_importants")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class FacteurImportantController {
    private final FacteurImportantInterface facteurImportantInterface;

    @GetMapping("/Zone/{zoneId}")
    public ResponseEntity<List<FacteurDTO>> getByZone(@PathVariable Long zoneId) {
        return ResponseEntity.ok(facteurImportantInterface.getValeursParZone(zoneId));
    }

    @PostMapping("/Ajouter")
    public ResponseEntity<FacteurImportant> add(@RequestBody FacteurImportant entity) {
        return ResponseEntity.ok(facteurImportantInterface.ajouterValeur(entity));
    }

    @PutMapping("/Modifier/{id}")
    public ResponseEntity<FacteurImportant> update(@PathVariable Long id, @RequestBody FacteurImportant entity) {
        return ResponseEntity.ok(facteurImportantInterface.modifierValeur(id, entity));
    }

    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facteurImportantInterface.supprimerValeur(id);
        return ResponseEntity.noContent().build();
    }
}
