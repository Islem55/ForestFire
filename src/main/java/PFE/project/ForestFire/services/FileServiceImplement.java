package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.FileServiceInterface;
import PFE.project.ForestFire.entities.FileEntity;
import PFE.project.ForestFire.repository.FileRepository;
import PFE.project.ForestFire.repository.UserRepo; // Import gardé
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
    private final UserRepo userRepo;


    public FileServiceImplement(FileRepository fileRepository, UserRepo userRepo) {
        this.fileRepository = fileRepository;
        this.userRepo = userRepo;
    }
    /**Enregistre le contenu complet du fichier directement DANS la base de données.**/
  /* @Override
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
    Path imagePath1 = Paths.get(System.getProperty("user.dir"), "uploads", "pdf");
    Path imagePath2 = Paths.get(System.getProperty("user.dir"), "uploads", "images");


    /** Enregistre le fichier PHYSIQUEMENT sur le disque dur du serveur avec un nom aléatoire.

     * Utilité : Évite d'alourdir la base de données en utilisant le système de fichiers (plus performant).

     **/


    /**@Override
    @Transactional // Ajouté pour lier l'user
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
            FileEntity savedFile = fileRepository.save(newfile);

            // LIEN AVEC L'UTILISATEUR
            userRepo.findById(userId).ifPresent(user -> {
                user.setPhotoProfil(savedFile);
                userRepo.save(user);
            });

            return finalName;

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
**/
    @Override
    @Transactional
    public String saveFile(MultipartFile file, Long userId, String customName) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new RuntimeException("Nom fichier invalide");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            // MODIFICATION ICI : On ajoute toujours un timestamp pour forcer l'unicité
            // et éviter les problèmes de cache navigateur.
            String finalName = "user_" + userId + "_" + System.currentTimeMillis() + extension;

            // Création de l'entité File
            FileEntity newfile = new FileEntity();
            newfile.setFileName(finalName);

            // Sauvegarde physique sur le disque
            Path targetPath = extension.equals(".pdf") ? imagePath1 : imagePath2;
            Files.createDirectories(targetPath); // Crée le dossier s'il n'existe pas
            Files.copy(file.getInputStream(), targetPath.resolve(finalName), StandardCopyOption.REPLACE_EXISTING);

            // Sauvegarde en Base de données
            FileEntity savedFile = fileRepository.save(newfile);

            // LIEN AVEC L'UTILISATEUR
            userRepo.findById(userId).ifPresent(user -> {
                user.setPhotoProfil(savedFile);
                userRepo.save(user);
            });

            return finalName;

        } catch (Exception e) {
            log.error("Erreur saveFile: ", e);
            throw new RuntimeException("Erreur lors de l'enregistrement : " + e.getMessage());
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

/**

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
**/
    @Transactional // Ajoute ceci pour assurer la cohérence


    public String updateFile(String filename, Long userId, MultipartFile newFile) {
   // 1. Récupérer l'utilisateur
UserEntity user = userRepo.findById(userId)
                      .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
// 2. Identifier l'ancienne photo
FileEntity anciennePhoto = user.getPhotoProfil();

if (anciennePhoto != null) {
         // 3. Détacher la photo de l'utilisateur d'abord
user.setPhotoProfil(null);
userRepo.saveAndFlush(user); // Force la mise à jour en base immédiatement

         // 4. Maintenant on peut supprimer l'ancien fichier sans erreur SQL
this.deleteFile(anciennePhoto.getFileName());
}

     // 5. Sauvegarder le nouveau fichier
String newFilename = this.saveFile(newFile, userId, null);

return newFilename;
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