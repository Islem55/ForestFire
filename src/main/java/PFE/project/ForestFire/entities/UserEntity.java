package PFE.project.ForestFire.entities;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name= "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="le nom est obligatoire")
    private String nom;

    @NotBlank(message="Le prénom est obligatoire ")
    private String prenom;

    @NotBlank(message="Email obligatoire")
    @Email(message="Email invalide")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@gmail\\.com$",
            message = "L'email doit être au format @gmail.com"
    )    @Column(unique=true)
    private String email;

    //@NotBlank(message="Mot de passe obligatoire")
    @Size(min=6, message="Mot de passe  doit contenir au moins 6 caractères")
    private String motDePasse;

    @Temporal(TemporalType.DATE)
    private Date dateDeCreation;

    @ManyToOne
    @JoinColumn(name="role_id")
    private RoleEntity role;
}
