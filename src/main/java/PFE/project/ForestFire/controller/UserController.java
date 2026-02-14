package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.entities.UserEntity;
import PFE.project.ForestFire.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserInterface userinterface;

    @PostMapping("/add")
    public UserEntity addUser(@RequestBody UserEntity user){
        return userinterface.adduser(user);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userinterface.deleteUser(id);
        return "Utilisateur supprimé avec succès";
    }

    @PutMapping("/updateuser/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user){
        return userinterface.updateUser(user,id);
    }

    @GetMapping("/allUsers")
    public List<UserEntity> getAllUsers(){
        return userinterface.getAllUsers();
    }

    @GetMapping("/findById/{id}")
    public UserEntity findUserById(@PathVariable Long id){
        return userinterface.getUserById(id);
    }

    @GetMapping("/findByUserName/{name}")
    public UserEntity findByUsername(@PathVariable String name){
        return userinterface.getUsrByName(name);
    }
}
