package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "affectations")
public class AffectationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L'Admin ou le Forestier concerné
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity forestier;
    // Le Secteur concerné
    @ManyToOne
    @JoinColumn(name = "secteur_id")
    private SecteurEntity secteur;

    // L'attribut supplémentaire pour l'action
    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAffectation = new Date();
}