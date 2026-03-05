package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.RoleName;
import PFE.project.ForestFire.entities.UserEntity;
import java.util.List;

public interface UserInterface {

    UserEntity adduser(UserEntity user);

    void deleteUser(Long id);

    UserEntity updateUser(UserEntity users, Long id);

    List<UserEntity> getAllUsers();

    UserEntity getUserById(Long id);

    UserEntity getUsersByName(String nom);

    UserEntity addUserWithRole(UserEntity user, RoleName roleName);}
