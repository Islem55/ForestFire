package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.RoleName;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.UserInterface;
import PFE.project.ForestFire.repository.UserRepo;
import PFE.project.ForestFire.repository.RoleRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static PFE.project.ForestFire.entities.RoleName.VISITEUR;

@Service
@RequiredArgsConstructor

public class UserImplement implements UserInterface {


    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity adduser(UserEntity user) {
        // 1. Encodage mot de passe
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));

        // 2. Gestion du rôle (Sécurisée)
        // On cherche TOUS les rôles portant ce nom au cas où il y aurait un doublon
        List<RoleEntity> roles = roleRepo.findAllByRoleName(RoleName.VISITEUR);

        RoleEntity role;
        if (!roles.isEmpty()) {
            // On prend le premier trouvé s'il y en a plusieurs
            role = roles.get(0);
        } else {
            // On le crée s'il n'existe pas du tout
            role = new RoleEntity();
            role.setRoleName(RoleName.VISITEUR);
            role = roleRepo.save(role);
        }

        user.setRole(role);

        // 3. Date de création
        if (user.getDateDeCreation() == null) {
            user.setDateDeCreation(new Date());
        }

        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id){
        userRepo.deleteById(id);
    }



    @Override
    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
    @Override
    public UserEntity getUsersByName(String nom) {
        return userRepo.findByNom(nom).orElse(null);
    }

    @Override
    public UserEntity updateUser(UserEntity user, Long id) {
        return userRepo.findById(id)
                .map(u -> {
                    u.setNom(user.getNom());
                    u.setPrenom(user.getPrenom());
                    u.setEmail(user.getEmail());
                    u.setAdresse(user.getAdresse());
                    u.setTelephone(user.getTelephone());

                    //  Ne changer le mot de passe QUE s'il est fourni
                    if (user.getMotDePasse() != null
                            && !user.getMotDePasse().trim().isEmpty()) {
                        u.setMotDePasse(user.getMotDePasse());
                    }
                    // si motDePasse null ou vide → on garde l'ancien en base

                    return userRepo.save(u);
                })
                .orElse(null);
    }

    @Override
    public UserEntity addUserWithRole(UserEntity user, RoleName roleName) {

        System.out.println("SERVICE - motDePasse reçu : " + user.getMotDePasse());

        // Vérification mot de passe
        if (user.getMotDePasse() == null || user.getMotDePasse().trim().isEmpty()) {
            throw new RuntimeException("Mot de passe obligatoire");
        }

        // Chercher ou créer le rôle
        Optional<RoleEntity> optionalRole = roleRepo.findByRoleName(roleName);
        RoleEntity role = optionalRole.orElseGet(() -> {
            RoleEntity r = new RoleEntity();
            r.setRoleName(roleName);
            return roleRepo.save(r);
        });

        user.setRole(role);

        if (user.getDateDeCreation() == null) {
            user.setDateDeCreation(new Date());
        }

        return userRepo.save(user);
    }


    @Override
    public List<UserEntity> getUsersByRole(RoleName roleName) {
        return userRepo.findByRoleRoleName(roleName);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}

