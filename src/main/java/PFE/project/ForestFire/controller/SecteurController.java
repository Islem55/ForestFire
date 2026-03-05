package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.SecteurGeoJSONDTO;
import PFE.project.ForestFire.entities.SecteurEntity;
import PFE.project.ForestFire.interfaces.SecteurInterface;
import PFE.project.ForestFire.mapper.SecteurMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secteurs")
@CrossOrigin("*")
public class SecteurController {

    private final SecteurInterface secteurInterface;

    public SecteurController(SecteurInterface secteurInterface) {
        this.secteurInterface = secteurInterface;
    }

    // Ajouter un secteur
    @PostMapping("/AjouterSecteur")
    public ResponseEntity<SecteurEntity> ajouterSecteur(@RequestBody SecteurEntity secteur) {
        SecteurEntity savedSecteur = secteurInterface.saveSecteur(secteur);
        return ResponseEntity.ok(savedSecteur);
    }

    // Afficher tous les secteurs (GeoJSON)
    @GetMapping("/AfficherToutSecteurs")
    public ResponseEntity<List<SecteurGeoJSONDTO>> afficherToutSecteur() {

        List<SecteurEntity> secteurs = secteurInterface.getAllSecteurs();

        List<SecteurGeoJSONDTO> result = secteurs
                .stream()
                .map(SecteurMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    // Afficher secteur par ID (GeoJSON)
    @GetMapping("/AfficherSecteurAvecId/{id}")
    public ResponseEntity<SecteurGeoJSONDTO> afficherSecteur(@PathVariable Long id){

        SecteurEntity secteur = secteurInterface.getSecteurById(id);

        if(secteur == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(SecteurMapper.toDTO(secteur));
    }

    // Filtrer par nom secteur
    @GetMapping("/nom_secteur/{nomSecteur}")
    public ResponseEntity<List<SecteurGeoJSONDTO>> secteurSource(@PathVariable String nomSecteur) {

        List<SecteurEntity> secteurs = secteurInterface.getByNomSecteur(nomSecteur);

        List<SecteurGeoJSONDTO> result = secteurs
                .stream()
                .map(SecteurMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    // Filtrer par gouvernorat
    @GetMapping("/gouvernorat/{nomGov}")
    public ResponseEntity<List<SecteurGeoJSONDTO>> findByNomGov(@PathVariable String nomGov) {

        List<SecteurEntity> secteurs = secteurInterface.getByGovernorate(nomGov);

        List<SecteurGeoJSONDTO> result = secteurs
                .stream()
                .map(SecteurMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

    // Supprimer
    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> deleteSecteur(@PathVariable Long id) {
        secteurInterface.deleteSecteur(id);
        return ResponseEntity.noContent().build();
    }
}