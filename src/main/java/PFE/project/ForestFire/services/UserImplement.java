package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.RoleName;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.UserInterface;
import PFE.project.ForestFire.repository.UserRepo;
import PFE.project.ForestFire.repository.RoleRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserImplement implements UserInterface {


    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    @Override
    public UserEntity adduser(UserEntity users){
        return userRepo.save(users);
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

                    // ✅ Ne changer le mot de passe QUE s'il est fourni
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
}

