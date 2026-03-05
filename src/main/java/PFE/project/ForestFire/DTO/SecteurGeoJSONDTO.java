package PFE.project.ForestFire.DTO;

import lombok.Data;

/**
 * DTO utilisé pour envoyer les données du secteur vers le frontend.
 * Il contient seulement les informations nécessaires pour l'affichage
 * sur la carte (Leaflet).
 *
 * - id : identifiant du secteur dans la base de données
 * - nomSecteur : nom du secteur
 * - geometry : géométrie du secteur convertie en format GeoJSON
 *
 * Ce DTO évite d'envoyer toute l'entité SecteurEntity et permet
 * de transformer la géométrie (MultiPolygon) en texte GeoJSON
 * que Leaflet peut lire pour afficher le secteur sur la carte.
 */
@Data
public class SecteurGeoJSONDTO {

    private Long id;          // Identifiant du secteur

    private String nomSecteur; // Nom du secteur

    private String geometry;   // Géométrie du secteur au format GeoJSON
}