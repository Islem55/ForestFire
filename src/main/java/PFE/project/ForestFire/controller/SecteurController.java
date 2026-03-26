package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.SecteurGeoJSONDTO;
import PFE.project.ForestFire.DTO.SecteurGroupeDTO;
import PFE.project.ForestFire.entities.SecteurEntity;
import PFE.project.ForestFire.interfaces.SecteurInterface;
import PFE.project.ForestFire.mapper.SecteurMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secteurs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecteurController {

    private final SecteurInterface secteurInterface;

    public SecteurController(SecteurInterface secteurInterface) {
        this.secteurInterface = secteurInterface;
    }

    // Ajouter une ligne secteur
    @PostMapping("/AjouterSecteur")
    public ResponseEntity<SecteurEntity> ajouterSecteur(
            @RequestBody SecteurEntity secteur) {
        return ResponseEntity.ok(
                secteurInterface.saveSecteur(secteur));
    }

    // Tous les secteurs GeoJSON plat
    @GetMapping("/AfficherToutSecteurs")
    public ResponseEntity<List<SecteurGeoJSONDTO>> afficherTout() {
        return ResponseEntity.ok(
                secteurInterface.getAllSecteurs().stream()
                        .map(SecteurMapper::toDTO).toList());
    }

    // ✅ Secteurs groupés par nomSecteur avec tous gouvernorats
    @GetMapping("/AfficherSecteursGroupes")
    public ResponseEntity<List<SecteurGroupeDTO>> afficherGroupes() {
        return ResponseEntity.ok(
                SecteurMapper.toGroupedDTOList(
                        secteurInterface.getAllSecteurs()));
    }

    // Modifier par ID
    @PutMapping("/Modifier/{id}")
    public ResponseEntity<?> modifier(
            @PathVariable Long id,
            @RequestBody SecteurEntity secteur) {
        SecteurEntity existing = secteurInterface.getSecteurById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        existing.setNomSecteur(secteur.getNomSecteur());
        existing.setNomGov(secteur.getNomGov());
        existing.setDescription(secteur.getDescription());
        return ResponseEntity.ok(secteurInterface.saveSecteur(existing));
    }

    // ✅ Supprimer TOUTES les lignes d'un secteur par nomSecteur
    @DeleteMapping("/SupprimerTout/{nomSecteur}")
    public ResponseEntity<?> supprimerTout(
            @PathVariable String nomSecteur) {
        secteurInterface.deleteSecteurByNomSecteur(nomSecteur);
        return ResponseEntity.ok(
                "Secteur '" + nomSecteur + "' supprimé.");
    }

    // Supprimer une ligne par ID
    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        secteurInterface.deleteSecteur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gouvernorat/{nomGov}")
    public ResponseEntity<List<SecteurGeoJSONDTO>> parGouv(
            @PathVariable String nomGov) {
        return ResponseEntity.ok(
                secteurInterface.getByGovernorate(nomGov).stream()
                        .map(SecteurMapper::toDTO).toList());
    }
}