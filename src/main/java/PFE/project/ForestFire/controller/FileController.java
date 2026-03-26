package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.interfaces.FileServiceInterface;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*",allowedHeaders = "*")

public class FileController {

    // Injection de dépendance du service de gestion des fichiers
// (permet d'accéder aux méthodes métier définies dans FileServiceInterface)
    private final FileServiceInterface fileService;

    public FileController (FileServiceInterface fileService) {
        this.fileService=fileService;

    }
/*
    @PostMapping("/ajouter")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.uploadfile(file));
    }

    @GetMapping("/télécharger/{filename}")
    public ResponseEntity<?> download(@PathVariable("filename") String filename) {
        ResponseEntity<?> response=fileService.downloadfile(filename);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            FileEntity file =(FileEntity) response.getBody();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFileName()+"\"")
                    .body(file.getData());
        }
        else{
            return new ResponseEntity<>("ficher n'est pas trouver ",HttpStatus.NOT_FOUND);
        }
    }*/
@PostMapping("/ajouter")
public ResponseEntity<?> uploadFolder(
        @RequestParam("file") MultipartFile file,
        @RequestParam("userId") Long userId,
        @RequestParam(value = "name", required = false) String name) {

    String savedFilename = fileService.saveFile(file,userId, name);

    return ResponseEntity.ok("File saved with name: " + savedFilename);
}

    //Il permet de lire un fichier depuis le dossier
    @GetMapping("/telecharger/{filename}")
    public ResponseEntity<?> downloadFolder(@PathVariable("filename") String filename) {

        byte[] fileData = fileService.afficherfile(filename);

        String mimeType = URLConnection.guessContentTypeFromName(filename);

        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM.toString();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(fileData);
    }
    @PutMapping("/modifier/{filename}")
    public ResponseEntity<?> update(
            @PathVariable String filename,
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile newFile) {

        String updated = fileService.updateFile(filename, userId,newFile);
        return ResponseEntity.ok("Fichier modifié : " + updated);
    }


    @DeleteMapping("/supprimer/{filename}")
    public ResponseEntity<?> delete(@PathVariable String filename) {
        fileService.deleteFile(filename);
        return ResponseEntity.ok("Fichier supprimé");
    }


    @GetMapping("/rechercher/{filename}")
    public ResponseEntity<?> rechercher(@PathVariable String filename) {

        byte[] fileData = fileService.afficherfile(filename);

        if (fileData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Fichier non trouvé");
        }

        String mimeType = URLConnection.guessContentTypeFromName(filename);

        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM.toString();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(fileData);
    }




}
