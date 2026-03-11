package PFE.project.ForestFire.DTO;

import lombok.Data;
import org.locationtech.jts.geom.MultiPolygon;

@Data
public class ZoneForestiereDTO {
    private Long id_0;
    private String id; // ID provenant de QGIS
    private String Nom_deleg;
    private String Nom_gov;
    private Long deleg_id;
    private MultiPolygon geom; // Sera converti en GeoJSON par Jackson-JTS
    private Long secteurId;
}