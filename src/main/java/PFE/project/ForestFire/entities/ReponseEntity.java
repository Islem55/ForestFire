package PFE.project.ForestFire.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "reponses")
public class ReponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "affectation_id")
    private AffectationEntity affectation;

    //  Statut adapté aux actions terrain
    @Enumerated(EnumType.STRING)
    private StatutReponse statut;

    //  Gravité adaptée aux incendies
    @Enumerated(EnumType.STRING)
    private Gravite gravite;

    private String commentaire;

    private String coordonneeGPS;

    //  Présence d'incendie confirmée ou non
    private Boolean incendieConfirme;

    //  Superficie estimée (pour action superficie)
    private Double superficieEstimee;



    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReponse = new Date();
}