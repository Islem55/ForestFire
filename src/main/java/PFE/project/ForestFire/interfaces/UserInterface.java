package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.UserEntity;
import java.util.List;

public interface UserInterface {

    public UserEntity adduser(UserEntity user);

    public String deleteUser(Long id);

    public UserEntity updateUser(UserEntity users, Long id);

    public List<UserEntity> getAllUsers();

    public UserEntity getUserById(Long id);

    public UserEntity getUsrByName(String name);

}
