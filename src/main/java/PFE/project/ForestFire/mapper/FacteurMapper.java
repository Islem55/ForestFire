package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.FacteurDTO;
import PFE.project.ForestFire.entities.DelegationEntity;
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
        if (important == null) return null;

        FacteurDTO dto = new FacteurDTO();

        // ✅ Fix 3 : l'ID du DTO doit être celui du FacteurEntity, pas de FacteurImportant
        dto.setFacteurImportantId(important.getId());  // ID de la valeur extraite
        dto.setValeur(important.getValeur());
        dto.setDateExtraction(important.getDate());

        // Métadonnées du facteur associé
        FacteurEntity facteur = important.getFacteurEntity();
        if (facteur != null) {
            dto.setId(facteur.getId());           // ✅ ID du facteur (pas de FacteurImportant)
            dto.setNom(facteur.getNom());
            dto.setCode(facteur.getCode());
            dto.setUnite(facteur.getUnite());
            dto.setDate(facteur.getDate());
            if (facteur.getTypeFacteur() != null) {
                dto.setTypeFacteur(facteur.getTypeFacteur());
            }
        }

        // ✅ Ajout : zone forestière associée
        // Zone (Delegation)
        DelegationEntity zone = important.getDelegationEntity();
        if (zone != null) {
            dto.setZoneId(zone.getId());
            dto.setNomZone(
                    zone.getNomDeleg() != null ? zone.getNomDeleg() : zone.getNomGov()
            );
        }

        return dto;
    }
}