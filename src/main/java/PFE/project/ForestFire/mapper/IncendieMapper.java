package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.IncendieGeoJSONDTO;
import PFE.project.ForestFire.entities.IncendieEntity;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

public class IncendieMapper {

    public static IncendieGeoJSONDTO toDTO(IncendieEntity entity) {
        if (entity == null) return null;

        IncendieGeoJSONDTO dto = new IncendieGeoJSONDTO();
        dto.setId(entity.getId());
        dto.setBrightness(entity.getBrightness());
        dto.setConfLvl(entity.getConfLvl());
        dto.setDayNight(entity.getDayNight());
        dto.setDtDet(entity.getDtDet());
        dto.setHrDet(entity.getHrDet());

        // Conversion du Point en String GeoJSON
        if (entity.getGeom() != null) {
            GeoJsonWriter writer = new GeoJsonWriter();
            dto.setGeometry(writer.write(entity.getGeom()));
        }

        return dto;
    }
}