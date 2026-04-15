package PFE.project.ForestFire.DTO;

import lombok.Data;
import org.locationtech.jts.geom.MultiPolygon;

@Data
public class DelegationGeoJSONDTO {
    private Long   id;
    private String nomDeleg;   // nom de la délégation
    private String nomGov;     // gouvernorat parent
    private String geometry;   // GeoJSON string
    private String delegNa1;




}