package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import java.util.Date;

@Entity
@Table(name = "incendies")
@Getter
@Setter
public class IncendieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "BRIGHTNESS")
    private Double brightness;

    @Column(name = "SCAN")
    private Double scan;

    @Column(name = "TRACK")
    private Double track;

    @Column(name = "dt_det")
    private String dtDet;

    @Column(name = "hr_det")
    private String hrDet;

    @Column(name = "conf_lvl")
    private String confLvl;

    @Column(name = "temp_T31")
    private Double tempT31;

    @Column(name = "FRP")
    private Double frp;

    @Column(name = "day_night")
    private String dayNight;

    @Column(name = "TYPE")
    private Integer type;

    /*
     Geometrie PostGIS
    */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date= new Date();

    @Column(name = "geom", columnDefinition = "geometry(Point,4326)")
    private Point geom;


    // Automatisme : Transforme X,Y en Point Géométrique avant de sauvegarder
    @PrePersist
    @PreUpdate
    public void generateGeom() {
        if (this.latitude != null && this.longitude != null) {
            GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
            // X = Longitude, Y = Latitude
            this.geom = factory.createPoint(new Coordinate(this.longitude, this.latitude));
        }


    }
}