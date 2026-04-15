package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.FacteurDTO;
import PFE.project.ForestFire.entities.DelegationEntity;
import PFE.project.ForestFire.entities.FacteurEntity;
import PFE.project.ForestFire.entities.FacteurImportant;
import PFE.project.ForestFire.entities.TypeFacteur;
import PFE.project.ForestFire.interfaces.DelegationInterface;
import PFE.project.ForestFire.interfaces.FacteurInterface;
import PFE.project.ForestFire.interfaces.FacteurImportantInterface;
import PFE.project.ForestFire.services.RasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("facteurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class FacteurController {

    private final FacteurInterface          facteurInterface;
    private final FacteurImportantInterface facteurImportantInterface;
    private final DelegationInterface   delegationInterface;
    private final RasterService             rasterService;

    // ── CRUD ──────────────────────────────────────────────────────────────────

    @PostMapping("/Ajouter")
    public ResponseEntity<FacteurEntity> add(@RequestBody FacteurEntity facteur) {
        System.out.println("=== [AJOUTER] Reçu : " + facteur);
        try {
            FacteurEntity saved = facteurInterface.ajouterFacteur(facteur);
            System.out.println("=== [AJOUTER] ✅ Sauvegardé avec ID : " + saved.getId());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            System.out.println("=== [AJOUTER] ❌ ERREUR : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/AfficherTout/all")
    public ResponseEntity<List<FacteurEntity>> getAll() {
        System.out.println("=== [GET ALL] Appel reçu");
        try {
            List<FacteurEntity> list = facteurInterface.getAllFacteurs();
            System.out.println("=== [GET ALL] ✅ Nombre facteurs : " + list.size());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            System.out.println("=== [GET ALL] ❌ ERREUR : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/AfficherAvecValeurs")
    public ResponseEntity<List<FacteurDTO>> getAllAvecValeurs() {
        System.out.println("=== [AVEC VALEURS] Appel reçu");
        try {
            List<FacteurDTO> list = facteurInterface.getAllFacteursAvecValeurs();
            System.out.println("=== [AVEC VALEURS] ✅ Nombre DTOs : " + list.size());
            if (!list.isEmpty()) {
                System.out.println("=== [AVEC VALEURS] Premier DTO : " + list.get(0));
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            System.out.println("=== [AVEC VALEURS] ❌ ERREUR : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/Afficher/{id}")
    public ResponseEntity<FacteurEntity> getById(@PathVariable Long id) {
        System.out.println("=== [GET BY ID] id=" + id);
        FacteurEntity f = facteurInterface.getFacteurById(id);
        if (f == null) {
            System.out.println("=== [GET BY ID] ❌ Facteur non trouvé pour id=" + id);
            return ResponseEntity.notFound().build();
        }
        System.out.println("=== [GET BY ID] ✅ Trouvé : " + f.getNom());
        return ResponseEntity.ok(f);
    }

    @GetMapping("/Rechercher/nom")
    public ResponseEntity<List<FacteurEntity>> getByNom(@RequestParam String nom) {
        System.out.println("=== [RECHERCHER NOM] nom=" + nom);
        return ResponseEntity.ok(facteurInterface.getFacteurByNom(nom));
    }

    @GetMapping("/filter/type/{type}")
    public ResponseEntity<List<FacteurEntity>> getByType(@PathVariable TypeFacteur type) {
        System.out.println("=== [FILTER TYPE] type=" + type);
        return ResponseEntity.ok(facteurInterface.getFacteurByType(type));
    }

    @PutMapping("/Modifier/{id}")
    public ResponseEntity<FacteurEntity> update(
            @PathVariable Long id,
            @RequestBody FacteurEntity facteur) {
        System.out.println("=== [MODIFIER] id=" + id + " | data=" + facteur);
        try {
            FacteurEntity updated = facteurInterface.updateFacteur(id, facteur);
            System.out.println("=== [MODIFIER] ✅ Modifié : " + updated.getNom());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            System.out.println("=== [MODIFIER] ❌ ERREUR : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("=== [SUPPRIMER] id=" + id);
        try {
            facteurInterface.deleteFacteur(id);
            System.out.println("=== [SUPPRIMER] ✅ Supprimé id=" + id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println("=== [SUPPRIMER] ❌ ERREUR : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // ── Upload Raster ─────────────────────────────────────────────────────────

    @PostMapping("/{facteurId}/upload-raster")
    public ResponseEntity<?> uploadRaster(
            @PathVariable Long facteurId,
            @RequestParam Long zoneId,
            @RequestParam("file") MultipartFile file) {

        System.out.println("=== [UPLOAD RASTER] ── Début ──");
        System.out.println("=== [UPLOAD RASTER] facteurId=" + facteurId);
        System.out.println("=== [UPLOAD RASTER] zoneId=" + zoneId);
        System.out.println("=== [UPLOAD RASTER] fichier=" + file.getOriginalFilename());
        System.out.println("=== [UPLOAD RASTER] taille=" + file.getSize() + " bytes");

        // Vérification fichier vide
        if (file.isEmpty()) {
            System.out.println("=== [UPLOAD RASTER] ❌ Fichier vide !");
            return ResponseEntity.badRequest().body("Fichier vide.");
        }

        // Vérification extension
        String filename = file.getOriginalFilename();
        if (filename == null ||
                (!filename.endsWith(".tif") && !filename.endsWith(".tiff"))) {
            System.out.println("=== [UPLOAD RASTER] ❌ Format invalide : " + filename);
            return ResponseEntity.badRequest()
                    .body("Format invalide. Seuls .tif/.tiff acceptés.");
        }

        try {
            // ÉTAPE 1 — Extraction raster
            System.out.println("=== [UPLOAD RASTER] ÉTAPE 1 : Extraction raster...");
            double valeurMoyenne = rasterService.extraireValeurMoyenne(file);
            System.out.println("=== [UPLOAD RASTER] ✅ Valeur moyenne extraite : "
                    + valeurMoyenne);

            // ÉTAPE 2 — Récupération du facteur
            System.out.println("=== [UPLOAD RASTER] ÉTAPE 2 : Recherche facteur id="
                    + facteurId);
            FacteurEntity facteur = facteurInterface.getFacteurById(facteurId);
            if (facteur == null) {
                System.out.println("=== [UPLOAD RASTER] ❌ Facteur introuvable id="
                        + facteurId);
                return ResponseEntity.notFound().build();
            }
            System.out.println("=== [UPLOAD RASTER] ✅ Facteur trouvé : "
                    + facteur.getNom());

            // ÉTAPE 3 — Récupération de la zone
            System.out.println("=== [UPLOAD RASTER] ÉTAPE 3 : Recherche zone id="
                    + zoneId);
            DelegationEntity zone = delegationInterface.getZoneById(zoneId);
            if (zone == null) {
                System.out.println("=== [UPLOAD RASTER] ❌ Zone introuvable id="
                        + zoneId);
                return ResponseEntity.notFound().build();
            }
            System.out.println("=== [UPLOAD RASTER] ✅ Zone trouvée : "
                    + zone.getNomDeleg());

            // ÉTAPE 4 — Sauvegarde FacteurImportant
            System.out.println("=== [UPLOAD RASTER] ÉTAPE 4 : Sauvegarde FacteurImportant...");
            FacteurImportant fi = new FacteurImportant();
            fi.setValeur(valeurMoyenne);
            fi.setFacteurEntity(facteur);
            fi.setDelegationEntity(zone);
            FacteurImportant saved = facteurImportantInterface.ajouterValeur(fi);
            System.out.println("=== [UPLOAD RASTER] ✅ FacteurImportant sauvegardé id="
                    + saved.getId());

            // ÉTAPE 5 — Nom de la zone
            String nomZone = zone.getNomDeleg() != null
                    ? zone.getNomDeleg()
                    : zone.getNomGov();
            System.out.println("=== [UPLOAD RASTER] ✅ Nom zone : " + nomZone);

            System.out.println("=== [UPLOAD RASTER] ── Terminé avec succès ──");

            return ResponseEntity.ok(new RasterUploadResponse(
                    saved.getId(),
                    facteur.getNom(),
                    facteur.getCode(),
                    nomZone,
                    valeurMoyenne,
                    filename
            ));

        } catch (Exception e) {
            System.out.println("=== [UPLOAD RASTER] ❌ EXCEPTION : " + e.getMessage());
            System.out.println("=== [UPLOAD RASTER] ❌ Cause : " +
                    (e.getCause() != null ? e.getCause().getMessage() : "null"));
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Erreur lors du traitement du raster : " + e.getMessage());
        }
    }

    @PostMapping("/{facteurId}/upload-raster-stats")
    public ResponseEntity<?> uploadRasterStats(
            @PathVariable Long facteurId,
            @RequestParam Long zoneId,
            @RequestParam("file") MultipartFile file) {

        System.out.println("=== [RASTER STATS] facteurId=" + facteurId
                + " | zoneId=" + zoneId);
        try {
            System.out.println("=== [RASTER STATS] Extraction statistiques...");
            RasterService.RasterStats stats = rasterService.extraireStatistiques(file);
            System.out.println("=== [RASTER STATS] ✅ moyenne=" + stats.moyenne()
                    + " min=" + stats.min() + " max=" + stats.max());

            FacteurEntity facteur = facteurInterface.getFacteurById(facteurId);
            System.out.println("=== [RASTER STATS] Facteur=" +
                    (facteur != null ? facteur.getNom() : "NULL ❌"));

            DelegationEntity zone = delegationInterface.getZoneById(zoneId);
            System.out.println("=== [RASTER STATS] Zone=" +
                    (zone != null ? zone.getNomDeleg() : "NULL ❌"));

            FacteurImportant fi = new FacteurImportant();
            fi.setValeur(stats.moyenne());
            fi.setFacteurEntity(facteur);
            fi.setDelegationEntity(zone);
            FacteurImportant saved = facteurImportantInterface.ajouterValeur(fi);
            System.out.println("=== [RASTER STATS] ✅ Sauvegardé id=" + saved.getId());

            return ResponseEntity.ok(new RasterStatsResponse(
                    saved.getId(), facteur.getNom(),
                    stats.moyenne(), stats.min(), stats.max(),
                    stats.ecartType(), stats.nombrePixels()
            ));

        } catch (Exception e) {
            System.out.println("=== [RASTER STATS] ❌ ERREUR : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Erreur : " + e.getMessage());
        }
    }

    // ── Records ───────────────────────────────────────────────────────────────

    public record RasterUploadResponse(
            Long   facteurImportantId,
            String nomFacteur,
            String codeFacteur,
            String nomZone,
            double valeurExtraite,
            String nomFichier
    ) {}

    public record RasterStatsResponse(
            Long   facteurImportantId,
            String nomFacteur,
            double moyenne,
            double min,
            double max,
            double ecartType,
            long   nombrePixels
    ) {}
}
