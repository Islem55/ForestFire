package PFE.project.ForestFire.config;

/**
 * Configuration Jackson pour supporter les géométries JTS dans l'API.
 *
 * Dans cette application SIG, les entités contiennent des objets géométriques
 * comme MultiPolygon provenant de la bibliothèque JTS (Java Topology Suite).
 *
 * Par défaut, Jackson (la bibliothèque utilisée par Spring Boot pour convertir
 * les objets Java en JSON) ne sait pas comment sérialiser ces types géométriques.
 *
 * Le module JtsModule permet d'ajouter le support des objets géométriques
 * JTS afin de pouvoir les convertir correctement en JSON/GeoJSON dans
 * les réponses de l'API REST.
 *
 * Cela permet au frontend cartographique (par exemple avec Leaflet)
 * de recevoir les géométries et de les afficher sur la carte.
 */

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    // Enregistre le module JTS dans Jackson pour permettre
    // la conversion des objets Geometry (MultiPolygon, Polygon, Point...) en JSON
    @Bean
    public Module jtsModule() {
        return new JtsModule();
    }

}