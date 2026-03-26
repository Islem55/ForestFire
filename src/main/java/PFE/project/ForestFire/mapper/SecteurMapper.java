package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.SecteurGeoJSONDTO;
import PFE.project.ForestFire.DTO.SecteurGroupeDTO;
import PFE.project.ForestFire.entities.SecteurEntity;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

import java.util.*;
import java.util.stream.Collectors;

public class SecteurMapper {

    // Mapper simple GeoJSON
    public static SecteurGeoJSONDTO toDTO(SecteurEntity s) {
        SecteurGeoJSONDTO dto = new SecteurGeoJSONDTO();
        dto.setId(s.getId());
        dto.setNomSecteur(s.getNomSecteur());
        dto.setDescription(s.getDescription());
        dto.setNomGov(s.getNomGov());
        if (s.getGeom() != null) {
            GeoJsonWriter writer = new GeoJsonWriter();
            dto.setGeometry(writer.write(s.getGeom()));
        }
        return dto;
    }

    // ✅ Grouper par nomSecteur — clé principale
    public static List<SecteurGroupeDTO> toGroupedDTOList(
            List<SecteurEntity> secteurs) {

        // Grouper toutes les lignes par nomSecteur
        Map<String, List<SecteurEntity>> grouped = secteurs.stream()
                .filter(s -> s.getNomSecteur() != null
                        && !s.getNomSecteur().trim().isEmpty())
                .collect(Collectors.groupingBy(
                        SecteurEntity::getNomSecteur,
                        LinkedHashMap::new,         // garder l'ordre
                        Collectors.toList()
                ));

        List<SecteurGroupeDTO> result = new ArrayList<>();

        grouped.forEach((nomSecteur, list) -> {
            SecteurGroupeDTO dto = new SecteurGroupeDTO();

            // nomSecteur = clé principale
            dto.setNomSecteur(nomSecteur);

            // Premier ID pour modification
            dto.setId(list.get(0).getId());

            // Tous les IDs pour suppression complète
            List<Long> ids = list.stream()
                    .map(SecteurEntity::getId)
                    .collect(Collectors.toList());
            dto.setIds(ids);

            // Description — première non nulle
            String desc = list.stream()
                    .map(SecteurEntity::getDescription)
                    .filter(d -> d != null && !d.trim().isEmpty())
                    .findFirst().orElse("");
            dto.setDescription(desc);

            // ✅ Tous les gouvernorats uniques triés
            List<String> gouvernorats = list.stream()
                    .map(SecteurEntity::getNomGov)
                    .filter(g -> g != null && !g.trim().isEmpty())
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            dto.setGouvernorats(gouvernorats);

            // Nombre de gouvernorats
            dto.setNombreGouvernorats(gouvernorats.size());

            result.add(dto);
        });

        // Trier par nomSecteur
        result.sort(Comparator.comparing(SecteurGroupeDTO::getNomSecteur));

        return result;
    }
}