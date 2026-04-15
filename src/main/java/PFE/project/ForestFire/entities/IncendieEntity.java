package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.*;
import java.util.Date;

@Entity
@Table(name = "incendies")
@Getter
@Setter
public class IncendieEntity {

    @Id
    private Long id;

    // ── Coordonnées ajoutées ─────────────────────
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // ── Dates ────────────────────────────────────
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "initialdat")
    private Date initialDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finaldate")
    private Date finalDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    // ── Infos générales ──────────────────────────
    @Column(name = "area_ha")
    private Double areaHa;

    @Column(name = "iso2")
    private String iso2;

    @Column(name = "iso3")
    private String iso3;

    @Column(name = "country")
    private String country;

    // ── Administration ───────────────────────────
    @Column(name = "admlvl1")
    private String admlvl1;

    @Column(name = "admlvl2")
    private String admlvl2;

    @Column(name = "admlvl3")
    private String admlvl3;

    @Column(name = "admlvl5")
    private String admlvl5;

    // ── Source ───────────────────────────────────
    @Column(name = "map_source")
    private String mapSource;

    // ── Types de couverture ──────────────────────
    @Column(name = "broadleave")
    private Double broadleave;

    @Column(name = "coniferous")
    private Double coniferous;

    @Column(name = "mixed_fore")
    private Double mixedFore;

    @Column(name = "sclerophil")
    private Double sclerophil;

    @Column(name = "transition")
    private Double transition;

    @Column(name = "other_natu")
    private Double otherNatu;

    @Column(name = "agricultur")
    private Double agricultur;

    @Column(name = "artificial")
    private Double artificial;

    @Column(name = "other_perc")
    private Double otherPerc;

    @Column(name = "natura2k_p")
    private Double natura2kP;

    // ── Autres ───────────────────────────────────
    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "eu_area")
    private String euArea;

    // ── Géométrie PostGIS ────────────────────────
    @Column(name = "geom", columnDefinition = "geometry(Point,4326)")
    private Point geom;

    // ── Génération automatique du point ─────────
    @PrePersist
    @PreUpdate
    public void generateGeom() {
        if (latitude != null && longitude != null) {
            GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
            this.geom = factory.createPoint(new Coordinate(longitude, latitude));
        }
    }
}