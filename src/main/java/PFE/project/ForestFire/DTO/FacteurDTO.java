package PFE.project.ForestFire.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la présentation des données Raster sur la carte.
 * Regroupe les infos de FacteurEntity et les valeurs de FacteurImportant.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacteurDTO {

    private Long id;

    // Informations provenant de FacteurEntity (Le type de raster)
    private String nom;       // ex: "Pente du terrain"
    private String code;      // ex: "SLOPE"
    private String type;      // ex: "TOPOGRAPHIQUE"
    private String unite;     // ex: "%", "°C", "m"

    // Information provenant de FacteurImportant (La mesure réelle)
    private Double valeur;    // ex: 15.5

}