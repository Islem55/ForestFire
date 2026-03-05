package PFE.project.ForestFire.mapper;

import PFE.project.ForestFire.DTO.SecteurGeoJSONDTO;
import PFE.project.ForestFire.entities.SecteurEntity;
import org.wololo.jts2geojson.GeoJSONWriter;

/**
 * Cette classe sert à convertir un objet SecteurEntity
 * (provenant de la base de données) en objet SecteurGeoJSONDTO.
 *
 * Le but est de transformer la géométrie (MultiPolygon)
 * en format GeoJSON afin qu'elle puisse être utilisée
 * dans le frontend (ex : affichage sur une carte Leaflet).
 */
public class SecteurMapper {

    /**
     * Cette méthode transforme un SecteurEntity en SecteurGeoJSONDTO
     */
    public static SecteurGeoJSONDTO toDTO(SecteurEntity secteur) {

        // Objet permettant de convertir une géométrie JTS en GeoJSON
        GeoJSONWriter writer = new GeoJSONWriter();

        // Création de l'objet DTO qui sera envoyé au frontend
        SecteurGeoJSONDTO dto = new SecteurGeoJSONDTO();

        // Copie des informations simples
        dto.setId(secteur.getId());
        dto.setNomSecteur(secteur.getNomSecteur());

        // Si la géométrie existe, on la convertit en GeoJSON
        if(secteur.getGeom() != null){
            dto.setGeometry(writer.write(secteur.getGeom()).toString());
        }

        // Retourne l'objet DTO prêt à être envoyé dans la réponse JSON
        return dto;
    }
}