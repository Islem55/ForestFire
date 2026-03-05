package PFE.project.ForestFire.DTO;
/**
 * DTO de réponse après authentification.
 * Contient le token JWT et les informations de l'utilisateur connecté.
 */
import lombok.Data;
import PFE.project.ForestFire.entities.UserEntity;

@Data
public class AuthResponseDTO {

    private String accessToken;
    private String tokenType = "Bearer";
    private UserEntity user;

    public AuthResponseDTO(String accessToken, UserEntity user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}