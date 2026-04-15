package PFE.project.ForestFire.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
/**
@Entity
@Data
@Table(name = "affectations")
public class AffectationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    // @JsonBackReference RETIRÉ — il cachait le forestier
    @JsonIgnoreProperties({"affectations", "motDePasse", "rapports", "dateDeCreation"})
    private UserEntity forestier;

    @ManyToOne
    @JoinColumn(name = "secteur_id")
    @JsonIgnoreProperties({"geom", "affectations"})
    private SecteurEntity secteur;

    @OneToMany(mappedBy = "affectation", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("affectation")
    private List<ReponseEntity> reponses;

    private String action;

    private String date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAffectation = new Date();
}**/
@Entity
@Table(name = "affectations")
public class AffectationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity forestier;

    @ManyToOne
    @JoinColumn(name = "secteur_id")
    private SecteurEntity secteur;

    @ManyToOne
    @JoinColumn(name = "gestionnaire_id") // 🔥 important
    private UserEntity gestionnaire;

    private String action;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAffectation = new Date();

    // ✅ GETTERS / SETTERS

    public Long getId() {
        return id;
    }

    public UserEntity getForestier() {
        return forestier;
    }

    public void setForestier(UserEntity forestier) {
        this.forestier = forestier;
    }

    public SecteurEntity getSecteur() {
        return secteur;
    }

    public void setSecteur(SecteurEntity secteur) {
        this.secteur = secteur;
    }

    public UserEntity getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(UserEntity gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(Date dateAffectation) {
        this.dateAffectation = dateAffectation;
    }
}