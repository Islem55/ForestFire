package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.IncendieDeclarationDTO;
import PFE.project.ForestFire.DTO.IncendieGeoJSONDTO;
import PFE.project.ForestFire.entities.IncendieEntity;

import java.text.SimpleDateFormat;

public class IncendieMapper {

    // ── DeclarationDTO → Entity ─────────────────
    public static IncendieEntity toEntity(IncendieDeclarationDTO dto) {

        IncendieEntity entity = new IncendieEntity();

        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());

        entity.setAdmlvl1(dto.getAdmlvl1());
        entity.setAdmlvl2(dto.getAdmlvl2());

        entity.setAreaHa(dto.getAreaHa());
        entity.setCountry(dto.getCountry());

        entity.setMapSource(dto.getMapSource());

        return entity;
    }

    // ── Entity → GeoJSON DTO ────────────────────
    public static IncendieGeoJSONDTO toDTO(IncendieEntity entity) {

        IncendieGeoJSONDTO dto = new IncendieGeoJSONDTO();

        // ── Geometry ─────────────────────────────
        IncendieGeoJSONDTO.Geometry geometry = new IncendieGeoJSONDTO.Geometry();

        if (entity.getLongitude() != null && entity.getLatitude() != null) {
            geometry.setCoordinates(new double[]{
                    entity.getLongitude(),
                    entity.getLatitude()
            });
        }

        dto.setGeometry(geometry);

        // ── Properties ───────────────────────────
        IncendieGeoJSONDTO.Properties props = new IncendieGeoJSONDTO.Properties();

        props.setId(entity.getId());
        props.setLatitude(entity.getLatitude());
        props.setLongitude(entity.getLongitude());

        // Dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (entity.getInitialDate() != null)
            props.setInitialDate(sdf.format(entity.getInitialDate()));

        if (entity.getFinalDate() != null)
            props.setFinalDate(sdf.format(entity.getFinalDate()));

        if (entity.getUpdated() != null)
            props.setUpdated(sdf.format(entity.getUpdated()));

        // Infos générales
        props.setAreaHa(entity.getAreaHa());
        props.setIso2(entity.getIso2());
        props.setIso3(entity.getIso3());
        props.setCountry(entity.getCountry());

        // Admin
        props.setAdmlvl1(entity.getAdmlvl1());
        props.setAdmlvl2(entity.getAdmlvl2());
        props.setAdmlvl3(entity.getAdmlvl3());
        props.setAdmlvl5(entity.getAdmlvl5());

        // Source
        props.setMapSource(entity.getMapSource());

        // Occupation sol
        props.setBroadleave(entity.getBroadleave());
        props.setConiferous(entity.getConiferous());
        props.setMixedFore(entity.getMixedFore());
        props.setSclerophil(entity.getSclerophil());
        props.setTransition(entity.getTransition());
        props.setOtherNatu(entity.getOtherNatu());
        props.setAgricultur(entity.getAgricultur());
        props.setArtificial(entity.getArtificial());
        props.setOtherPerc(entity.getOtherPerc());
        props.setNatura2kP(entity.getNatura2kP());

        // Autres
        props.setAreaCode(entity.getAreaCode());
        props.setEuArea(entity.getEuArea());

        dto.setProperties(props);

        return dto;
    }
}