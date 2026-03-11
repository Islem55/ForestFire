package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.ZoneForestiereDTO;
import PFE.project.ForestFire.entities.ZoneForestiereEntity;

public class ZoneForestiereMapper {

    public static ZoneForestiereDTO toDTO(ZoneForestiereEntity entity) {
        if (entity == null) return null;

        ZoneForestiereDTO dto = new ZoneForestiereDTO();
        dto.setId_0(entity.getId_0());
        dto.setId(entity.getId());
        dto.setNom_deleg(entity.getNom_deleg());
        dto.setNom_gov(entity.getNom_gov());
        dto.setDeleg_id(entity.getDeleg_id());
        dto.setGeom(entity.getGeom());

        if (entity.getSecteurEntity() != null) {
            dto.setSecteurId(entity.getSecteurEntity().getId());
        }

        return dto;
    }
}