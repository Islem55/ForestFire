package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.ZoneGroupeDTO;
import PFE.project.ForestFire.entities.ZoneEntity;

import java.util.*;
import java.util.stream.Collectors;

public class ZoneMapper {

    public static List<ZoneGroupeDTO> toGroupedDTOList(List<ZoneEntity> zones) {
        Map<String, List<ZoneEntity>> grouped = zones.stream()
                .filter(z -> z.getNomZone() != null && !z.getNomZone().trim().isEmpty())
                .collect(Collectors.groupingBy(
                        ZoneEntity::getNomZone,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<ZoneGroupeDTO> result = new ArrayList<>();

        grouped.forEach((nomZone, list) -> {
            ZoneGroupeDTO dto = new ZoneGroupeDTO();
            dto.setNomZone(nomZone);
            dto.setId(list.get(0).getId());
            dto.setIds(list.stream().map(ZoneEntity::getId).collect(Collectors.toList()));
            dto.setNomGov(list.get(0).getNomGov());
            dto.setNomSecteur(list.get(0).getNomSecteur());
            dto.setTypeZone(list.get(0).getTypeZone());
            dto.setCouleur(list.stream()
                    .map(ZoneEntity::getCouleur)
                    .filter(c -> c != null && !c.trim().isEmpty())
                    .findFirst().orElse("#e85d04"));
            dto.setDelegations(list.stream()
                    .map(ZoneEntity::getNomDele)
                    .filter(d -> d != null && !d.trim().isEmpty())
                    .distinct().sorted().collect(Collectors.toList()));
            dto.setNombreDelegations(dto.getDelegations().size());
            result.add(dto);
        });

        result.sort(Comparator.comparing(ZoneGroupeDTO::getNomZone));
        return result;
    }
}