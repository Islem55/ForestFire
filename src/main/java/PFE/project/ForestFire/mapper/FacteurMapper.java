package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.FacteurDTO;
import PFE.project.ForestFire.entities.FacteurEntity;
import PFE.project.ForestFire.entities.FacteurImportant;
import org.springframework.stereotype.Component;

@Component
public class FacteurMapper {

    /**
     * Transforme une entité FacteurImportant (donnée raster par zone)
     * en un FacteurDTO (format pour le web/carte).
     */
    public FacteurDTO toDto(FacteurImportant important) {
        if (important == null) {
            return null;
        }

        FacteurDTO dto = new FacteurDTO();

        // 1. Récupération de l'ID et de la valeur de la donnée
        dto.setId(important.getId());
        dto.setValeur(important.getValeur());

        // 2. Récupération des métadonnées du facteur associé (Pente, Climat, etc.)
        FacteurEntity facteur = important.getFacteurEntity();
        if (facteur != null) {
            dto.setNom(facteur.getNom());
            dto.setCode(facteur.getCode());
            dto.setUnite(facteur.getUnite());

            // Conversion de l'énumération en String pour le DTO
            if (facteur.getTypeFacteur() != null) {
                dto.setType(facteur.getTypeFacteur().name());
            }
        }

        return dto;
    }
}