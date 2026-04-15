package PFE.project.ForestFire.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
/**
@Entity
@Table(name = "affectation_zone")
@Data
public class AffectationZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAffectation = new Date();

    @ManyToOne
    @JoinColumn(name = "zone_id")
    @JsonIgnoreProperties({"affectations", "secteur", "date"})
    private DelegationEntity delegation;

    @ManyToOne
    @JoinColumn(name = "forestier_id")
    @JsonIgnoreProperties({"affectations", "affectationsCreees", "secteur",
            "rapports", "photoProfil", "motDePasse"})
    private UserEntity forestier;

    @ManyToOne
    @JoinColumn(name = "gestionnaire_id")
    @JsonIgnoreProperties({"affectations", "affectationsCreees", "secteurs",
            "rapports", "photoProfil", "motDePasse"})
    private UserEntity gestionnaire;

}**/

@Entity
@Table(name = "affectation_zone")
@Data
public class AffectationZoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAffectation = new Date();

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ZoneEntity zone;

    @ManyToOne
    @JoinColumn(name = "forestier_id")
    private UserEntity forestier;

    @ManyToOne
    @JoinColumn(name = "gestionnaire_id")
    private UserEntity gestionnaire;
}

