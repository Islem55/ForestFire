package PFE.project.ForestFire.DTO;

import lombok.Data;

/**
 * DTO utilisé pour envoyer les données du secteur vers le frontend.
 *
 * Rôle : Représente UNE SEULE ligne de la table secteur
 * sous forme de GeoJSON — utilisé pour l'affichage sur la carte Leaflet.
 *
 * Ce DTO évite d'exposer toute l'entité SecteurEntity et transforme
 * la géométrie (MultiPolygon JTS) en String GeoJSON lisible par Leaflet.
 */
@Data
public class SecteurGeoJSONDTO {

    // ID technique de la ligne en base
    private Long id;

    // Nom du secteur (clé logique — peut être partagé entre plusieurs lignes)
    private String nomSecteur;

    // Description du secteur
    private String description;

    // Gouvernorat de cette ligne spécifique
    private String nomGov;

    // Nom de la délégation si disponible
    private String nomDele;

    // Géométrie MultiPolygon sérialisée en GeoJSON String
    // Ex: {"type":"MultiPolygon","coordinates":[[[[9.1,36.2],...]]]}
    private String geometry;
}