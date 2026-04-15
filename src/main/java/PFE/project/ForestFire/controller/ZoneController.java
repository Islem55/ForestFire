package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.ZoneGroupeDTO;
import PFE.project.ForestFire.interfaces.ZoneInterface;
import PFE.project.ForestFire.mapper.ZoneMapper;
import PFE.project.ForestFire.repository.ZoneRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zones")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ZoneController {

    private final ZoneInterface zoneInterface;
    private final ZoneRepo      zoneRepo;

    public ZoneController(ZoneInterface zoneInterface, ZoneRepo zoneRepo) {
        this.zoneInterface = zoneInterface;
        this.zoneRepo      = zoneRepo;
    }

    // ── Toutes les zones groupées ──
    @GetMapping("/AfficherZonesGroupes")
    public ResponseEntity<List<ZoneGroupeDTO>> afficherGroupes() {
        return ResponseEntity.ok(
                ZoneMapper.toGroupedDTOList(zoneInterface.getAllZones()));
    }

    // ── Ajouter une zone par délégations ──
    @PostMapping("/AjouterZone")
    public ResponseEntity<?> ajouterZone(@RequestBody Map<String, Object> body) {
        String nomZone    = (String) body.get("nomZone");
        String nomSecteur = (String) body.get("nomSecteur");
        String nomGov     = (String) body.get("nomGov");
        String couleur    = (String) body.get("couleur");

        @SuppressWarnings("unchecked")
        List<String> delegations = (List<String>) body.get("delegations");

        if (nomZone == null || nomSecteur == null || delegations == null || delegations.isEmpty()) {
            return ResponseEntity.badRequest().body("Données manquantes.");
        }

        try {
            return ResponseEntity.ok(
                    zoneInterface.ajouterZoneParDelegations(
                            nomZone, nomSecteur, nomGov, delegations, couleur));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // ── Modifier une zone ──
    @PutMapping("/ModifierZone/{ancienNom}")
    public ResponseEntity<?> modifier(
            @PathVariable String ancienNom,
            @RequestBody Map<String, Object> body) {

        String nouveauNom = (String) body.get("nomZone");
        String nomSecteur = (String) body.get("nomSecteur");
        String nomGov     = (String) body.get("nomGov");
        String couleur    = (String) body.get("couleur");

        @SuppressWarnings("unchecked")
        List<String> delegations = (List<String>) body.get("delegations");

        if (nouveauNom == null || delegations == null || delegations.isEmpty()) {
            return ResponseEntity.badRequest().body("Données manquantes.");
        }

        return ResponseEntity.ok(
                zoneInterface.modifierZone(
                        ancienNom, nouveauNom, nomSecteur, nomGov, delegations, couleur));
    }

    // ── Supprimer ──
    @DeleteMapping("/SupprimerZone/{nomZone}")
    public ResponseEntity<?> supprimer(@PathVariable String nomZone) {
        zoneInterface.deleteZoneByNomZone(nomZone);
        return ResponseEntity.ok("Zone '" + nomZone + "' supprimée.");
    }

    // ── Délégations utilisées (pour bloquer sur la carte) ──
    @GetMapping("/delegationsUtilisees")
    public ResponseEntity<List<String>> delegationsUtilisees() {
        return ResponseEntity.ok(zoneRepo.findAllUsedDelegations());
    }

    // ── Délégations utilisées en excluant une zone (pour modification) ──
    @GetMapping("/delegationsUtilisees/{nomZone}")
    public ResponseEntity<List<String>> delegationsExclure(
            @PathVariable String nomZone) {
        return ResponseEntity.ok(
                zoneRepo.findDelegationsUsedByOtherZones(nomZone));
    }

    // ── Récupérer le secteur du gestionnaire ──
    @GetMapping("/secteur-par-gestionnaire/{id}")
    public ResponseEntity<String> getSecteurByGestionnaire(@PathVariable Long id) {
        return ResponseEntity.ok(
                zoneInterface.getSecteurByGestionnaire(id)
        );
    }

    @GetMapping("/par-gestionnaire/{gestionnaireId}")
    public ResponseEntity<List<ZoneGroupeDTO>> getZonesByGestionnaire(
            @PathVariable Long gestionnaireId) {

        return ResponseEntity.ok(
                ZoneMapper.toGroupedDTOList(
                        zoneInterface.getZonesByGestionnaire(gestionnaireId)
                )
        );
    }
    @GetMapping("/par-secteur/{nomSecteur}")
    public ResponseEntity<List<ZoneGroupeDTO>> getZonesBySecteur(
            @PathVariable String nomSecteur) {

        return ResponseEntity.ok(
                ZoneMapper.toGroupedDTOList(
                        zoneInterface.getZonesBySecteur(nomSecteur)
                )
        );
    }


    @GetMapping("/par-gouvernorat/{nomGov}")
    public ResponseEntity<List<ZoneGroupeDTO>> getZonesByGov(
            @PathVariable String nomGov) {

        return ResponseEntity.ok(
                ZoneMapper.toGroupedDTOList(
                        zoneInterface.getZonesByGouvernorat(nomGov)
                )
        );
    }




}