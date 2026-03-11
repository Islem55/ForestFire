package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Resultat_Risque")
@Data
public class ResultatRisqueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer valeur;

}
