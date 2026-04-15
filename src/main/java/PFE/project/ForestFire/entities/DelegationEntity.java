package PFE.project.ForestFire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "delegation")
@Getter
@Setter
public class DelegationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hasc_2")
    private String hasc2;

    @Column(name = "circo_id")
    private String circoId;

    @Column(name = "Nom_deleg")
    private String nomDeleg;

    @Column(name = "deleg_na_1")
    private String delegNa1;

    @Column(name = "circo_name")
    private String circoName;

    @Column(name = "cico_na_1")
    private String cicoNa1;

    @Column(name = "gov_name_a")
    private String govNameA;

    @Column(name = "Nom_gov")
    private String nomGov;

    @Column(name = "adm_id")
    private String admId;

    @Column(name = "deleg_id")
    private String delegId;

    @Column(name = "gov_id")
    private String govId;

    @JdbcTypeCode(SqlTypes.GEOMETRY)
    @Column(columnDefinition = "geometry(MultiPolygon,4326)")
    private MultiPolygon geom;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    @ManyToOne
    @JoinColumn(name = "gestionnaire_id")
    private UserEntity gestionnaire;


    @ManyToOne
    @JoinColumn(name = "secteur_id")
    //@JsonIgnoreProperties({"affectations", "gestionnaire", "geom"})
    @JsonIgnore

    private SecteurEntity secteur;





    //@OneToMany(mappedBy = "delegation")
   // private List<HistoriqueRisqueEntity> historiqueRisqueEntity;
}
