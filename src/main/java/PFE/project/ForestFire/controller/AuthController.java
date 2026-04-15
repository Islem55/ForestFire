package PFE.project.ForestFire.controller;
/**
 * Contrôleur d'authentification.
 * Gère la création automatique d'un compte administrateur
 * et la connexion avec génération d'un token JWT.
 */
import PFE.project.ForestFire.DTO.AuthResponseDTO;
import PFE.project.ForestFire.DTO.LoginDTO;
import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.RoleName;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.repository.RoleRepo;
import PFE.project.ForestFire.repository.UserRepo;
import PFE.project.ForestFire.security.JWTGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final RoleRepo roleRepo;

    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, RoleRepo roleRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.roleRepo = roleRepo;
    }
    @PostConstruct
    public void createDefaultAdminAccount() {

        if (!userRepo.existsByNom("admin")) {

            UserEntity adminUser = new UserEntity();

            adminUser.setNom("admin");
            adminUser.setPrenom("Super");
            adminUser.setEmail("admin_forestfire@gmail.com");
            adminUser.setMotDePasse(passwordEncoder.encode("Admin@123"));

            adminUser.setDateDeCreation(new Date());
            RoleEntity adminRole = roleRepo
                    .findByRoleName(RoleName.ADMINISTRATEUR)
                    .orElseGet(() -> {
                        RoleEntity newRole = new RoleEntity();
                        newRole.setRoleName(RoleName.ADMINISTRATEUR);
                        return roleRepo.save(newRole);
                    });


            adminUser.setRole(adminRole);

            userRepo.save(adminUser);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            System.out.println("1. Tentative de connexion pour : " + loginDTO.getEmail());

            // ÉTAPE CRITIQUE : L'authentification
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getMotDePasse()
                    )
            );
            System.out.println("2. Authentification réussie !");

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("3. Génération du token...");
            String token = jwtGenerator.generateToken(authentication);

            System.out.println("4. Recherche de l'utilisateur en base...");
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserEntity user = userRepo.findByEmail(userDetails.getUsername()).orElse(null);

            if (user == null) {
                System.out.println("ERREUR : Utilisateur authentifié mais non trouvé dans userRepo !");
            }

            AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, user);
            System.out.println("5. Login terminé avec succès.");

            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            System.out.println("ERREUR : Mauvais mot de passe (BadCredentialsException)");
            return new ResponseEntity<>("Mot de passe incorrect", HttpStatus.UNAUTHORIZED);
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
            System.out.println("ERREUR : Email non trouvé (UsernameNotFoundException)");
            return new ResponseEntity<>("Email inexistant", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println("ERREUR INCONNUE : " + e.getClass().getName());
            e.printStackTrace();
            return new ResponseEntity<>("Erreur serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Nettoyer le contexte de sécurité de Spring
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Déconnexion réussie", HttpStatus.OK);
    }



}
