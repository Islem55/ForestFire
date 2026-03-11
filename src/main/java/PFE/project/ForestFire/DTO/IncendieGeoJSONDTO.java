package PFE.project.ForestFire.DTO;

import lombok.Data;

@Data
public class IncendieGeoJSONDTO {

    private Long id;
    private Double brightness;
    private String confLvl;
    private String dayNight;
    private String dtDet;
    private String hrDet;

    private String geometry;

}