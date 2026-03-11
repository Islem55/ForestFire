package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.RoleName;
import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.UserInterface;
import PFE.project.ForestFire.repository.UserRepo;
import PFE.project.ForestFire.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public UserEntity updateUser(UserEntity user, Long id){

        return userRepo.findById(id)
                .map(u -> {
                    u.setNom(user.getNom());
                    u.setPrenom(user.getPrenom());
                    u.setEmail(user.getEmail());
                    u.setMotDePasse(user.getMotDePasse());
                    u.setRole(user.getRole());
                    return userRepo.save(u);
                })
                .orElse(null);
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
    public UserEntity addUserWithRole(UserEntity user, RoleName roleName) {

        Optional<RoleEntity> optionalRole = roleRepo.findByRoleName(roleName);

        RoleEntity role = optionalRole.orElseGet(() -> {
            RoleEntity r = new RoleEntity();
            r.setRoleName(roleName);
            return roleRepo.save(r);
        });

        user.setRole(role);

        return userRepo.save(user);
    }




}

