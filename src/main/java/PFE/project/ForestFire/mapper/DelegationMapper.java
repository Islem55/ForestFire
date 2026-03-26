package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.DelegationGeoJSONDTO;
import PFE.project.ForestFire.entities.DelegationEntity;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

public class DelegationMapper {

    public static DelegationGeoJSONDTO toDTO(DelegationEntity d) {
        DelegationGeoJSONDTO dto = new DelegationGeoJSONDTO();
        dto.setId(d.getId());
        dto.setNomDeleg(d.getNomDeleg());
        dto.setNomGov(d.getNomGov());
        if (d.getGeom() != null) {
            GeoJsonWriter writer = new GeoJsonWriter();
            dto.setGeometry(writer.write(d.getGeom()));
        }
        return dto;
    }
}