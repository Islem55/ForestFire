package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="Resultat_Risque")
@Data
public class ResultatRisqueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer valeur;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date= new Date();
    @ManyToOne
    @JoinColumn(name = "zone_id") // Le nom de la colonne en BDD
    private DelegationEntity delegation; // <--- Ce nom doit être identique au mappedBy

}
