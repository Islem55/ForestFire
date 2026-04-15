package PFE.project.ForestFire.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncendieGeoJSONDTO {

    private String type = "Feature";
    private Geometry geometry;
    private Properties properties;

    @Getter
    @Setter
    public static class Geometry {
        private String type = "Point";
        private double[] coordinates; // [longitude, latitude]
    }

    @Getter
    @Setter
    public static class Properties {

        // ── Identité ─────────────────────────────
        private Long id;

        // ── Coordonnées ──────────────────────────
        private Double latitude;
        private Double longitude;

        // ── Dates ───────────────────────────────
        private String initialDate;
        private String finalDate;
        private String updated;

        // ── Infos générales ─────────────────────
        private Double areaHa;
        private String iso2;
        private String iso3;
        private String country;

        // ── Administration ──────────────────────
        private String admlvl1;
        private String admlvl2;
        private String admlvl3;
        private String admlvl5;

        // ── Source ──────────────────────────────
        private String mapSource;

        // ── Occupation du sol ───────────────────
        private Double broadleave;
        private Double coniferous;
        private Double mixedFore;
        private Double sclerophil;
        private Double transition;
        private Double otherNatu;
        private Double agricultur;
        private Double artificial;
        private Double otherPerc;
        private Double natura2kP;

        // ── Autres ──────────────────────────────
        private String areaCode;
        private String euArea;
    }
}