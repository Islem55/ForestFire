package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(name = "incendies")
@Getter
@Setter
public class IncendieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "brightness")
    private Double brightness;

    @Column(name = "scan")
    private Double scan;

    @Column(name = "track")
    private Double track;

    @Column(name = "dt_det")
    private String dtDet;

    @Column(name = "hr_det")
    private String hrDet;

    @Column(name = "conf_lvl")
    private String confLvl;

    @Column(name = "temp_t31")
    private Double tempT31;

    @Column(name = "frp")
    private Double frp;

    @Column(name = "day_night")
    private String dayNight;

    @Column(name = "type")
    private Integer type;

    /*
    Geometrie PostGIS
     */
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point geom;
}