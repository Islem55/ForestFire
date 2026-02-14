package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.UserInterface;
import PFE.project.ForestFire.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImplement implements UserInterface {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserEntity adduser(UserEntity users){
        return userRepo.save(users);
    }

    @Override
    public String deleteUser(Long id){
        userRepo.deleteById(id);
        return "deleted";
    }

    @Override
    public UserEntity updateUser(UserEntity user, Long id){

        UserEntity u = userRepo.findById(id).orElse(null);

        if(u != null){
            u.setNom(user.getNom());
            u.setPrenom(user.getPrenom());
            u.setEmail(user.getEmail());
            u.setMotDePasse(user.getMotDePasse());
            u.setRole(user.getRole());

            return userRepo.save(u);
        }

        return null;
    }

    @Override
    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }

    @Override
    public UserEntity getUserById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public UserEntity getUsrByName(String nom){
        Optional<UserEntity> user = userRepo.findByNom(nom);
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }
}
