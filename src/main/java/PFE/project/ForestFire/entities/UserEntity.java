package PFE.project.ForestFire.entities;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

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

    @NotBlank(message="Mot de passe obligatoire")
    @Size(min=6)
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;
    private String adresse;
    private Integer telephone ;

    @Temporal(TemporalType.DATE)
    private Date dateDeCreation;

    @OneToMany(mappedBy = "forestier", cascade = CascadeType.ALL)
    private List<AffectationEntity> affectations;

    @ManyToOne
    @JoinColumn(name="role_id")
    private RoleEntity role;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private FileEntity photoProfil;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<FileEntity> rapports;
}
