package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "zone")
@Getter
@Setter
public class ZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_zone", nullable = false)
    private String nomZone;

    @Column(name = "nom_dele", nullable = false)
    private String nomDele;

    @Column(name = "nom_gov")
    private String nomGov;

    @Column(name = "nom_secteur")
    private String nomSecteur;

    @Column(name = "couleur", length = 20)
    private String couleur;

    // ✅ type_zone : 'gov' ou 'dele'
    @Column(name = "type_zone", length = 10)
    private String typeZone;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();
    @ManyToOne
    private DelegationEntity delegation;
    @ManyToOne
    @JoinColumn(name = "gestionnaire_id")
    private UserEntity gestionnaire;
}