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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserInterface userinterface;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/ajouter")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user){
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
            user.setMotDePasse(passwordEncoder.encode(request.getUser().getMotDePasse()));
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

        UserEntity existingUser = userinterface.getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        // 1. On crée l'objet qui contiendra les modifications
        UserEntity userUpdates = new UserEntity();
        userUpdates.setNom(dto.getNom());
        userUpdates.setPrenom(dto.getPrenom());
        userUpdates.setEmail(dto.getEmail());
        userUpdates.setAdresse(dto.getAdresse());
        userUpdates.setTelephone(dto.getTelephone());

        // 2. Gestion du mot de passe
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isEmpty()) {
            userUpdates.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        } else {
            userUpdates.setMotDePasse(existingUser.getMotDePasse());
        }

        // 3. CRUCIAL : On préserve la photo existante !
        // Si on ne le fait pas, la photo sera supprimée (mise à null) en base
        userUpdates.setPhotoProfil(existingUser.getPhotoProfil());

        // On préserve aussi le rôle actuel s'il n'est pas dans le DTO
        userUpdates.setRole(existingUser.getRole());

        UserEntity updated = userinterface.updateUser(userUpdates, id);
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

    // Ajouter cette méthode dans votre UserController existant
    @GetMapping("/par-role/{roleName}")
    public ResponseEntity<List<UserEntity>> getUsersByRole(@PathVariable String roleName) {
        try {
            RoleName role = RoleName.valueOf(roleName);
            List<UserEntity> users = userinterface.getUsersByRole(role);
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/par-email/{email}")
    public ResponseEntity<?> parEmail(@PathVariable String email) {
        UserEntity user = userinterface.getUserByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("id", user.getId(), "nom", user.getNom(), "email", user.getEmail()));
    }
}