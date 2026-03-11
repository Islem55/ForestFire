package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.IncendieGeoJSONDTO;
import PFE.project.ForestFire.entities.IncendieEntity;
import org.locationtech.jts.io.WKTWriter;

public class IncendieMapper {

    public static IncendieGeoJSONDTO toDTO(IncendieEntity incendie){

        IncendieGeoJSONDTO dto = new IncendieGeoJSONDTO();

        dto.setId(incendie.getId());
        dto.setBrightness(incendie.getBrightness());
        dto.setConfLvl(incendie.getConfLvl());
        dto.setDayNight(incendie.getDayNight());
        dto.setDtDet(incendie.getDtDet().toString());
        dto.setHrDet(incendie.getHrDet().toString());

        // conversion geometry
        WKTWriter writer = new WKTWriter();
        dto.setGeometry(writer.write(incendie.getGeom()));

        return dto;
    }
}