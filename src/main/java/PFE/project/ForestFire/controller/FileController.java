package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.interfaces.FileServiceInterface;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;
import java.util.Map;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class FileController {

    private final FileServiceInterface fileService;

    public FileController(FileServiceInterface fileService) {
        this.fileService = fileService;
    }

    // ──────────────────────────────────────────
    // AJOUTER un fichier
    // ──────────────────────────────────────────
    @PostMapping("/ajouter")
    public ResponseEntity<?> uploadFolder(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "name", required = false) String name) {

        try {
            String savedFilename = fileService.saveFile(file, userId, name);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Fichier enregistré avec succès",
                    "fileName", savedFilename
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Erreur lors de l'enregistrement : " + e.getMessage()
            ));
        }
    }

    // ──────────────────────────────────────────
    // TÉLÉCHARGER un fichier (pour affichage)
    // ──────────────────────────────────────────
    @GetMapping("/telecharger/{filename}")
    public ResponseEntity<?> downloadFolder(@PathVariable String filename) {

        try {
            byte[] fileData = fileService.afficherfile(filename);

            String mimeType = URLConnection.guessContentTypeFromName(filename);
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM.toString();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(fileData);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Fichier introuvable : " + filename
            ));
        }
    }

    // ──────────────────────────────────────────
    // MODIFIER un fichier
    // ──────────────────────────────────────────
    @PutMapping("/modifier/{filename}")
    public ResponseEntity<?> update(
            @PathVariable String filename,
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile newFile) {

        try {
            String newFilename = fileService.updateFile(filename, userId, newFile);
            // ✅ Retourne du JSON — corrige l'erreur Angular
            return ResponseEntity.ok(Map.of(
                    "message", "Fichier modifié avec succès",
                    "fileName", newFilename
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Erreur lors de la modification : " + e.getMessage()
            ));
        }
    }

    // ──────────────────────────────────────────
    // SUPPRIMER un fichier
    // ──────────────────────────────────────────
    @DeleteMapping("/supprimer/{filename}")
    public ResponseEntity<?> delete(@PathVariable String filename) {

        try {
            fileService.deleteFile(filename);
            return ResponseEntity.ok(Map.of(
                    "message", "Fichier supprimé avec succès",
                    "fileName", filename
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Erreur lors de la suppression : " + e.getMessage()
            ));
        }
    }

    // ──────────────────────────────────────────
    // AFFICHER un fichier (pour <img src="...">)
    // ──────────────────────────────────────────
    @GetMapping("/rechercher/{filename}")
    public ResponseEntity<?> rechercher(@PathVariable String filename) {

        try {
            byte[] fileData = fileService.afficherfile(filename);

            String mimeType = URLConnection.guessContentTypeFromName(filename);
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM.toString();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(fileData);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Fichier non trouvé : " + filename
            ));
        }
    }
}