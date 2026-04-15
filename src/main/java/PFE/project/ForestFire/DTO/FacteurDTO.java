package PFE.project.ForestFire.DTO;

import PFE.project.ForestFire.entities.TypeFacteur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacteurDTO {

    private Long        id;
    private String      code;
    private String      nom;
    private TypeFacteur typeFacteur;
    private String      unite;
    private Date        date;

    private Long        facteurImportantId;
    private Double      valeur;
    private Date        dateExtraction;

    private Long        zoneId;
    private String      nomZone;
}