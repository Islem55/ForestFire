package PFE.project.ForestFire.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface FileServiceInterface {
     // ResponseEntity<?> uploadfile(MultipartFile file);
    //ResponseEntity<?> downloadfile(String nomFile);
     String saveFile(MultipartFile file,Long userId, String customName);
     byte[]afficherfile(String nomFile);
    String updateFile(String oldFileName, Long userId,MultipartFile newFile);

    void deleteFile(String fileName);
}
