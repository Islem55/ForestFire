package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.AffectationDTO;
import PFE.project.ForestFire.DTO.SecteurGeoJSONDTO;
import PFE.project.ForestFire.DTO.SecteurGroupeDTO;
import PFE.project.ForestFire.entities.SecteurEntity;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.SecteurInterface;
import PFE.project.ForestFire.interfaces.UserInterface;
import PFE.project.ForestFire.mapper.SecteurMapper;
import PFE.project.ForestFire.repository.SecteurRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/secteurs")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class SecteurController {

    private final SecteurInterface secteurInterface;
    private final UserInterface    userInterface;
    private final SecteurRepo      secteurRepo;

    public SecteurController(SecteurInterface secteurInterface,
                             UserInterface    userInterface,
                             SecteurRepo      secteurRepo) {
        this.secteurInterface = secteurInterface;
        this.userInterface    = userInterface;
        this.secteurRepo      = secteurRepo;
    }

    // ── Ajouter ──
    @PostMapping("/AjouterSecteur")
    public ResponseEntity<SecteurEntity> ajouterSecteur(
            @RequestBody SecteurEntity secteur) {
        return ResponseEntity.ok(secteurInterface.saveSecteur(secteur));
    }

    // ── Tous GeoJSON ──
    @GetMapping("/AfficherToutSecteurs")
    public ResponseEntity<List<SecteurGeoJSONDTO>> afficherTout() {
        return ResponseEntity.ok(
                secteurInterface.getAllSecteurs().stream()
                        .map(SecteurMapper::toDTO).toList());
    }

    // ── Groupés par nomSecteur ──
    @GetMapping("/AfficherSecteursGroupes")
    public ResponseEntity<List<SecteurGroupeDTO>> afficherGroupes() {
        return ResponseEntity.ok(
                SecteurMapper.toGroupedDTOList(
                        secteurInterface.getAllSecteurs()));
    }
    @GetMapping("/gestionnaire/{id}/secteurs")
    public List<SecteurEntity> getSecteurs(@PathVariable Long id) {
        return secteurRepo.findByGestionnaireId(id);
    }
    // ── Modifier par ID ──
    @PutMapping("/Modifier/{id}")
    public ResponseEntity<?> modifier(
            @PathVariable Long id,
            @RequestBody SecteurEntity secteur) {

        SecteurEntity existing = secteurInterface.getSecteurById(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setNomSecteur(secteur.getNomSecteur());
        existing.setNomGov(secteur.getNomGov());
        existing.setNomDele(secteur.getNomDele());
        existing.setDescription(secteur.getDescription());
        existing.setGestionnaire(secteur.getGestionnaire());

        if (secteur.getCouleur() != null && !secteur.getCouleur().trim().isEmpty()) {
            existing.setCouleur(secteur.getCouleur());
        }

        return ResponseEntity.ok(secteurInterface.saveSecteur(existing));
    }

    // ✅ NOUVEAU — Modifier tout un secteur par nomSecteur (UPDATE sans delete)
    @PutMapping("/ModifierSecteur/{ancienNom}")
    public ResponseEntity<?> modifierSecteurComplet(
            @PathVariable String ancienNom,
            @RequestBody Map<String, Object> body) {

        String nouveauNom   = (String) body.get("nomSecteur");
        String description  = (String) body.get("description");
        String couleur      = (String) body.get("couleur");

        @SuppressWarnings("unchecked")
        List<String> gouvernorats = (List<String>) body.get("gouvernorats");

        if (nouveauNom == null || gouvernorats == null || gouvernorats.isEmpty()) {
            return ResponseEntity.badRequest().body("Données manquantes");
        }

        List<SecteurEntity> result = secteurInterface.modifierSecteur(
                ancienNom, nouveauNom, gouvernorats, description, couleur);

        return ResponseEntity.ok(result);
    }

    // ✅ Modifier couleur
    @PutMapping("/ModifierCouleur/{nomSecteur}")
    public ResponseEntity<?> modifierCouleur(
            @PathVariable String nomSecteur,
            @RequestBody Map<String, String> body) {

        String couleur = body.get("couleur");
        if (couleur == null || couleur.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Couleur manquante");
        }
        secteurInterface.updateCouleurSecteur(nomSecteur, couleur);
        return ResponseEntity.ok("Couleur mise à jour pour : " + nomSecteur);
    }

    // ── Supprimer tout par nomSecteur ──
    @DeleteMapping("/SupprimerTout/{nomSecteur}")
    public ResponseEntity<?> supprimerTout(@PathVariable String nomSecteur) {
        secteurInterface.deleteSecteurByNomSecteur(nomSecteur);
        return ResponseEntity.ok("Secteur '" + nomSecteur + "' supprimé.");
    }

    // ── Supprimer par ID ──
    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        secteurInterface.deleteSecteur(id);
        return ResponseEntity.noContent().build();
    }

    // ── Par gouvernorat ──
    @GetMapping("/gouvernorat/{nomGov}")
    public ResponseEntity<List<SecteurGeoJSONDTO>> parGouv(
            @PathVariable String nomGov) {
        return ResponseEntity.ok(
                secteurInterface.getByGovernorate(nomGov).stream()
                        .map(SecteurMapper::toDTO).toList());
    }

    // ── Par délégation ──
    @GetMapping("/delegation/{nomDele}")
    public ResponseEntity<List<SecteurGeoJSONDTO>> parDele(
            @PathVariable String nomDele) {
        return ResponseEntity.ok(
                secteurInterface.getByDelegation(nomDele).stream()
                        .map(SecteurMapper::toDTO).toList());
    }

    // ── Délégations utilisées ──
    @GetMapping("/delegationsUtilisees")
    public ResponseEntity<List<String>> delegationsUtilisees() {
        return ResponseEntity.ok(secteurRepo.findAllUsedDelegations());
    }

    @GetMapping("/delegationsUtilisees/{nomSecteur}")
    public ResponseEntity<List<String>> delegationsUtiliseesExclure(
            @PathVariable String nomSecteur) {
        return ResponseEntity.ok(
                secteurRepo.findDelegationsUsedByOtherSecteurs(nomSecteur));
    }

    // ── Affecter gestionnaire ──
    @PutMapping("/affecterGestionnaire/{secteurId}/{userId}")
    public ResponseEntity<?> affecterGestionnaire(
            @PathVariable Long secteurId,
            @PathVariable Long userId) {

        SecteurEntity secteur = secteurInterface.getSecteurById(secteurId);
        if (secteur == null) return ResponseEntity.notFound().build();

        UserEntity user = userInterface.getUserById(userId);
        if (user == null) return ResponseEntity.notFound().build();

        if (!user.getRole().getRoleName().name().equals("GESTIONNAIRE")) {
            return ResponseEntity.badRequest()
                    .body("Cet utilisateur n'est pas gestionnaire");
        }

        secteur.setGestionnaire(user);
        return ResponseEntity.ok(secteurInterface.saveSecteur(secteur));
    }

    @PutMapping("/desaffecterGestionnaire/{secteurId}")
    public ResponseEntity<?> desaffecterGestionnaire(
            @PathVariable Long secteurId) {
        SecteurEntity secteur = secteurInterface.getSecteurById(secteurId);
        if (secteur == null) return ResponseEntity.notFound().build();
        secteur.setGestionnaire(null);
        return ResponseEntity.ok(secteurInterface.saveSecteur(secteur));
    }

    // ── Affectations ──
    @GetMapping("/affectations")
    public ResponseEntity<List<AffectationDTO>> getAffectations() {
        return ResponseEntity.ok(
                secteurInterface.getAllSecteurs().stream()
                        .filter(s -> s.getGestionnaire() != null)
                        .collect(java.util.stream.Collectors.toMap(
                                SecteurEntity::getNomSecteur,
                                s -> new AffectationDTO(
                                        s.getId(),
                                        s.getNomSecteur(),
                                        s.getGestionnaire().getId(),
                                        s.getGestionnaire().getNom(),
                                        s.getGestionnaire().getPrenom(),
                                        s.getGestionnaire().getEmail()
                                ),
                                (existing, replacement) -> existing
                        ))
                        .values().stream().toList()
        );
    }

    @GetMapping("/sansGestionnaire")
    public ResponseEntity<List<SecteurEntity>> getSansGestionnaire() {
        return ResponseEntity.ok(
                secteurInterface.getAllSecteurs().stream()
                        .filter(s -> s.getGestionnaire() == null)
                        .toList()
        );
    }

    // ── Secteurs du gestionnaire connecté ──
    @GetMapping("/AfficherSecteurssGroupes")
    public ResponseEntity<List<SecteurGroupeDTO>> afficherGroupess() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return ResponseEntity.status(401).build();

        String email = auth.getName();
        if (email == null) return ResponseEntity.status(401).build();

        UserEntity user = userInterface.getUserByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();

        List<SecteurEntity> secteurs =
                secteurInterface.getSecteursByGestionnaire(user.getId());

        return ResponseEntity.ok(SecteurMapper.toGroupedDTOListe(secteurs));
    }

    @DeleteMapping("/supprimerSiPasGestionnaire/{id}")
    public ResponseEntity<?> supprimerSiPasGestionnaire(@PathVariable Long id) {
        SecteurEntity secteur = secteurInterface.getSecteurById(id);
        if (secteur == null) return ResponseEntity.notFound().build();
        if (secteur.getGestionnaire() == null) {
            secteurInterface.deleteSecteur(id);
            return ResponseEntity.ok("Supprimé");
        }
        return ResponseEntity.badRequest().body("Impossible : secteur déjà affecté");
    }

    @GetMapping("/delegationsParGov/{nomGov}")
    public ResponseEntity<List<String>> delegationsParGov(
            @PathVariable String nomGov) {
        return ResponseEntity.ok(
                secteurRepo.findDelegationsByGov(nomGov));
    }
}