package PFE.project.ForestFire.DTO;

import PFE.project.ForestFire.entities.FileEntity;
import lombok.Data;

@Data
public class UserWithRoleRequest {

    private UserDTO user;
    private RoleDTO role;

    @Data
    public static class UserDTO {
        private String nom;
        private String prenom;
        private String email;
        private String motDePasse;
        private String adresse;
        private Integer telephone;
        private FileEntity photoProfil;
    }

    @Data
    public static class RoleDTO {
        private String roleName;
    }
}