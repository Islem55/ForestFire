package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.UserWithRoleRequest;
import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.RoleName;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.UserInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {

    @Autowired
    UserInterface userinterface;

    @PostMapping("/ajouter")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserEntity user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userinterface.adduser(user));
    }

    // ──────────────────────────────────────────
    // AJOUTER utilisateur avec rôle
    // ──────────────────────────────────────────
    @PostMapping("/ajouter_avec_role")
    public ResponseEntity<?> addUserWithRole(
            @RequestBody UserWithRoleRequest request) {
        try {
            // LOG pour vérifier dans la console Spring Boot
            System.out.println("=== NOM        : " + request.getUser().getNom());
            System.out.println("=== PRENOM     : " + request.getUser().getPrenom());
            System.out.println("=== EMAIL      : " + request.getUser().getEmail());
            System.out.println("=== MOT_PASSE  : " + request.getUser().getMotDePasse());
            System.out.println("=== ROLE       : " + request.getRole().getRoleName());

            // Construire UserEntity depuis DTO — sans annotations JPA
            UserEntity user = new UserEntity();
            user.setNom(request.getUser().getNom());
            user.setPrenom(request.getUser().getPrenom());
            user.setEmail(request.getUser().getEmail());
            user.setMotDePasse(request.getUser().getMotDePasse());
            user.setAdresse(request.getUser().getAdresse());
            user.setTelephone(request.getUser().getTelephone());

            // Convertir le String roleName en enum RoleName
            RoleName roleName = RoleName.valueOf(request.getRole().getRoleName());

            UserEntity saved = userinterface.addUserWithRole(user, roleName);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Rôle invalide : " + request.getRole().getRoleName());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ──────────────────────────────────────────
    // MODIFIER utilisateur
    // ──────────────────────────────────────────
    @PutMapping("/modifier/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserWithRoleRequest.UserDTO dto) {

        System.out.println("=== MODIFIER ID    : " + id);
        System.out.println("=== MODIFIER NOM   : " + dto.getNom());
        System.out.println("=== MODIFIER PASS  : " + dto.getMotDePasse());

        UserEntity existingUser = userinterface.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }

        // Construire UserEntity depuis DTO
        UserEntity user = new UserEntity();
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setMotDePasse(dto.getMotDePasse());  // null/vide = géré dans le service
        user.setAdresse(dto.getAdresse());
        user.setTelephone(dto.getTelephone());

        UserEntity updated = userinterface.updateUser(user, id);
        return ResponseEntity.ok(updated);
    }

    // ──────────────────────────────────────────
    // SUPPRIMER utilisateur
    // ──────────────────────────────────────────
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        UserEntity user = userinterface.getUserById(id);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }
        userinterface.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Utilisateur supprimé avec succès");
    }

    // ──────────────────────────────────────────
    // LISTER tous les utilisateurs
    // ──────────────────────────────────────────
    @GetMapping("/tout")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userinterface.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    // ──────────────────────────────────────────
    // TROUVER par ID
    // ──────────────────────────────────────────
    @GetMapping("/id_utilisateur/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        UserEntity user = userinterface.getUserById(id);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }
        return ResponseEntity.ok(user);
    }

    // ──────────────────────────────────────────
    // TROUVER par NOM
    // ──────────────────────────────────────────
    @GetMapping("/nom_utilisateur/{nom}")
    public ResponseEntity<?> findByUserName(@PathVariable String nom) {
        UserEntity user = userinterface.getUsersByName(nom);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }
        return ResponseEntity.ok(user);
    }
}