package PFE.project.ForestFire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.time.LocalTime;

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


    @Column(name = "geom", columnDefinition = "geometry(Point,4326)")
    private Point geom;

    // Méthode pour afficher l'heure

    public String getFormattedHour() {

        if (hrDet == null || hrDet.length() != 4) {
            return hrDet;
        }

        String h = hrDet.substring(0, 2);
        String m = hrDet.substring(2, 4);

        return h + ":" + m;
    }

    public String getFormattedDate() {

        if (dtDet == null) {
            return null;
        }

        return dtDet.toString();
    }


}