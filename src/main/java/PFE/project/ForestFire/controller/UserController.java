package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.UserWithRoleRequest;
import PFE.project.ForestFire.entities.RoleEntity;
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
public class UserController {

    @Autowired
    UserInterface userinterface;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserEntity user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userinterface.adduser(user));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        UserEntity user =userinterface.getUserById(id);
        if(user==null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }
        userinterface.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Utilisateur supprimé avec succès");    }

    @PutMapping("/updateUser/{id}")
   public ResponseEntity <?> updateUser (@PathVariable Long id ,@Valid @RequestBody UserEntity user){
        UserEntity existingUser =userinterface.getUserById(id);
        if(existingUser==null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }
        UserEntity updatedUser =userinterface.updateUser(user,id);
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/allUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users =userinterface.getAllUsers();
        if(users.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id){
        UserEntity user = userinterface.getUserById(id);
        if(user==null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");

        }
        return ResponseEntity.ok(user);
    }


    @GetMapping("/findByUserName/{nom}")
    public ResponseEntity<?> findByUserName(@PathVariable String nom){

        UserEntity user = userinterface.getUsersByName(nom);

        if(user == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Utilisateur non trouvé");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/addUserWithRole")
    public ResponseEntity<?> addUserWithRole(
            @Valid @RequestBody UserWithRoleRequest request){
        try{
            UserEntity savedUser =userinterface.addUserWithRole(request.getUser(),request.getRole().getRoleName());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUser);
        }
        catch(RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}



