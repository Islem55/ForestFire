package PFE.project.ForestFire.DTO;

import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.UserEntity;

import javax.management.relation.Role;

public class UserWithRoleRequest {
    private UserEntity user;
    private RoleEntity role;

    public UserEntity getUser(){
        return user;
    }
    public void setUser(UserEntity user){
        this.user=user;
    }
    public RoleEntity getRole(){
        return role;
    }
    public void setRole(RoleEntity role){
        this.role=role;
    }
}
