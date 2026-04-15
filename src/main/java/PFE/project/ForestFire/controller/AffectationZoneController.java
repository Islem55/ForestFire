package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.AffectationZoneDTO;
import PFE.project.ForestFire.entities.AffectationZoneEntity;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.entities.ZoneEntity;
import PFE.project.ForestFire.interfaces.AffectationZoneInterface;
import PFE.project.ForestFire.interfaces.UserInterface;
import PFE.project.ForestFire.interfaces.ZoneInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/affectations-forestier")
@CrossOrigin(origins = "http://localhost:4200")
public class AffectationZoneController {

    private final AffectationZoneInterface affectationService;
    private final UserInterface userService;
    private final ZoneInterface zoneService;

    public AffectationZoneController(
            AffectationZoneInterface affectationService,
            UserInterface userService,
            ZoneInterface zoneService) {

        this.affectationService = affectationService;
        this.userService = userService;
        this.zoneService = zoneService;
    }

    // ✅ 1. AFFECTATIONS DU GESTIONNAIRE
    @GetMapping("/mes-affectations/{gestionnaireId}")
    public ResponseEntity<List<AffectationZoneDTO>> mesAffectations(
            @PathVariable Long gestionnaireId) {

        List<AffectationZoneDTO> list = affectationService.getByGestionnaire(gestionnaireId)
                .stream()
                .map(a -> new AffectationZoneDTO(
                        a.getId(),
                        a.getZone().getId(),
                        a.getZone().getNomZone(),
                        a.getZone().getNomGov(),
                        a.getForestier().getId(),
                        a.getForestier().getNom(),
                        a.getForestier().getPrenom(),
                        a.getForestier().getEmail(),
                        a.getGestionnaire().getId(),
                        a.getGestionnaire().getNom(),
                        a.getGestionnaire().getPrenom(),
                        a.getDateAffectation().toString()
                ))
                .toList();

        return ResponseEntity.ok(list);
    }

    // ✅ 2. ZONES DU GESTIONNAIRE
    @GetMapping("/mes-zones/{gestionnaireId}")
    public ResponseEntity<?> mesZones(@PathVariable Long gestionnaireId) {

        return ResponseEntity.ok(
                zoneService.getZonesByGestionnaire(gestionnaireId)
                        .stream()
                        .map(z -> java.util.Map.of(
                                "id", z.getId(),
                                "nomZone", z.getNomZone(),
                                "nomGov", z.getNomGov()
                        ))
                        .toList()
        );
    }

    // ✅ 3. AFFECTER ZONE → FORESTIER
    @PostMapping("/affecter/{zoneId}/{forestierId}/{gestionnaireId}")
    public ResponseEntity<?> affecter(
            @PathVariable Long zoneId,
            @PathVariable Long forestierId,
            @PathVariable Long gestionnaireId) {

        try {
            ZoneEntity zone = zoneService.getZoneById(zoneId);
            UserEntity forestier = userService.getUserById(forestierId);
            UserEntity gestionnaire = userService.getUserById(gestionnaireId);

            // ✅ Vérification existence
            if (zone == null || forestier == null || gestionnaire == null) {
                return ResponseEntity.badRequest().body("Données invalides !");
            }

            // ✅ Vérification rôle
            if (!forestier.getRole().getRoleName().name().equals("FORESTIER")) {
                return ResponseEntity.badRequest()
                        .body("Cet utilisateur n'est pas un forestier !");
            }

            // ✅ Création affectation
            AffectationZoneEntity affectation = new AffectationZoneEntity();
            affectation.setZone(zone);
            affectation.setForestier(forestier);
            affectation.setGestionnaire(gestionnaire);

            affectationService.save(affectation);

            return ResponseEntity.ok("Affectation ajoutée ✅");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ 4. MODIFIER
    @PutMapping("/modifier/{affectationId}/{zoneId}/{forestierId}")
    public ResponseEntity<?> modifier(
            @PathVariable Long affectationId,
            @PathVariable Long zoneId,
            @PathVariable Long forestierId) {

        try {
            AffectationZoneEntity affectation =
                    affectationService.getById(affectationId);

            ZoneEntity zone = zoneService.getZoneById(zoneId);
            UserEntity forestier = userService.getUserById(forestierId);

            if (zone == null || forestier == null) {
                return ResponseEntity.badRequest().body("Données invalides !");
            }

            affectation.setZone(zone);
            affectation.setForestier(forestier);

            affectationService.save(affectation);

            return ResponseEntity.ok("Affectation modifiée ✅");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ 5. SUPPRIMER
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {

        try {
            affectationService.delete(id);
            return ResponseEntity.ok("Affectation supprimée ✅");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}