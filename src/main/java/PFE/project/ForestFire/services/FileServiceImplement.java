package PFE.project.ForestFire.services;

import PFE.project.ForestFire.interfaces.FileServiceInterface;
import PFE.project.ForestFire.entities.FileEntity;
import PFE.project.ForestFire.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
@Slf4j
public class FileServiceImplement implements FileServiceInterface {

    private static  final Logger LOGGER= LoggerFactory.getLogger(FileServiceImplement.class.getName());
    private final FileRepository fileRepository;


    public FileServiceImplement(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
/**Enregistre le contenu complet du fichier directement DANS la base de données.**/
  /*  @Override
    public ResponseEntity<?> uploadfile(MultipartFile file){

        try {

            // Vérifier si un fichier avec le même nom existe déjà dans la base de données
            if(!this.fileRepository.existsByFileName(file.getOriginalFilename())) {

                // Création d'un nouvel objet FileEntity
                FileEntity newFile = new FileEntity();

                // Enregistrer le nom du fichier
                newFile.setFileName(file.getOriginalFilename());

                // Enregistrer le type du fichier
                newFile.setContentType(file.getContentType());

                // Enregistrer la taille du fichier
                newFile.setSize(file.getSize());

                // Convertir le fichier en tableau de bytes pour le stocker dans la base
                newFile.setData(file.getBytes());



                return new ResponseEntity<>(
                        "Le fichier a été téléchargé avec succès"+fileRepository.save(newFile),
                        HttpStatus.CREATED
                );

            } else {

                // Message si le fichier existe déjà
                return new ResponseEntity<>(
                        "Le fichier existe déjà : " + file.getOriginalFilename(),
                        HttpStatus.CONFLICT
                );
            }

        } catch (IOException e){

            // Enregistrer l'erreur dans les logs
            LOGGER.error("Erreur lors de la récupération des données du fichier", e);


            return new ResponseEntity<>(
                    "Une erreur est survenue lors du traitement du fichier",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    @Transactional(readOnly = true) // <--- Indispensable pour les Large Objects (LOB) sur Postgres
    public ResponseEntity<?> downloadfile(String fileName){
        Optional<FileEntity> optionalfile =fileRepository.findByFileName(fileName);
        if(optionalfile.isPresent()){
            FileEntity file = optionalfile.get();
            return new ResponseEntity<>(file, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
*/
    Path imagePath1= Paths.get("uploads/pdf");
    Path imagePath2= Paths.get("uploads/images");
    /** Enregistre le fichier PHYSIQUEMENT sur le disque dur du serveur avec un nom aléatoire.

     * Utilité : Évite d'alourdir la base de données en utilisant le système de fichiers (plus performant).

     **/
    @Override
    public String saveFile(MultipartFile file,Long userId, String customName){

        try {

            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new RuntimeException("Nom fichier invalide");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            //  Choix du nom
            String finalName;

            if (customName != null && !customName.trim().isEmpty()) {
                finalName = customName + extension; // nom utilisateur
            } else {
                finalName = RandomStringUtils.randomAlphanumeric(10) + extension; // nom random
            }

            //  Création entity
            FileEntity newfile = new FileEntity();
            newfile.setFileName(finalName);

            // Choix dossier
            if (extension.equalsIgnoreCase(".pdf")) {
                Files.copy(file.getInputStream(), imagePath1.resolve(finalName), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(file.getInputStream(), imagePath2.resolve(finalName), StandardCopyOption.REPLACE_EXISTING);
            }

            // Sauvegarde DB
            fileRepository.save(newfile);

            return finalName;

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] afficherfile(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        try {
            Path filePath;

            if (extension.equals(".pdf")) {
                filePath = imagePath1.resolve(fileName);
            } else {
                filePath = imagePath2.resolve(fileName);
            }

            // le fichier existe-t-il vraiment sur le disque ?
            if (!Files.exists(filePath)) {
                throw new RuntimeException("Fichier introuvable sur le disque à l'emplacement : " + filePath);
            }

            return Files.readAllBytes(filePath);

        } catch (Exception e) {
            log.error("Erreur lors de la lecture du fichier " + fileName, e);
            throw new RuntimeException("Erreur de lecture : " + e.getMessage());
        }
    }



    @Override
    public String updateFile(String oldFileName,Long userId, MultipartFile newFile) {

        try {
            // supprimer ancien fichier
            deleteFile(oldFileName);

            // récupérer nom sans extension
            String nameWithoutExt = oldFileName.substring(0, oldFileName.lastIndexOf("."));

            // sauvegarder nouveau avec même nom
            return saveFile(newFile,userId, nameWithoutExt);

        } catch (Exception e) {
            throw new RuntimeException("Erreur update fichier", e);
        }
    }

    @Override
    @Transactional // Ajouté pour garantir que si un truc échoue, rien n'est supprimé
    public void deleteFile(String fileName) {
        try {
            // 1. SUPPRESSION PHYSIQUE (Sur le disque)
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            Path filePath;

            if (extension.equals(".pdf")) {
                filePath = Paths.get("uploads/pdf").resolve(fileName);
            } else {
                filePath = Paths.get("uploads/images").resolve(fileName);
            }

            // Supprime le fichier s'il existe sur le serveur
            Files.deleteIfExists(filePath);

            // 2. SUPPRESSION LOGIQUE (Dans la base de données)
            // On cherche le fichier par son nom et on le supprime via le repository
            fileRepository.findByFileName(fileName).ifPresent(file -> {
                fileRepository.delete(file);
            });

        } catch (Exception e) {
            log.error("Erreur lors de la suppression du fichier " + fileName, e);
            throw new RuntimeException("Erreur suppression fichier : " + e.getMessage());
        }
    }


}

