package PFE.project.ForestFire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.List;
import java.util.Date;

@Entity
@Table(name = "secteur")
@Getter
@Setter
public class SecteurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"ID_0\"")
    private Integer id0;

    @Column(name = "\"ISO\"")
    private String iso;

    @Column(name = "\"Nom_pay\"")
    private String nomPay;

    @Column(name = "\"ID_1\"")
    private Integer id1;

    @Column(name = "\"Nom_gov\"")
    private String nomGov;

    @Column(name = "\"ID_2\"")
    private Integer id2;

    @Column(name = "\"Nom_dele\"")
    private String nomDele;

    @Column(name = "\"HASC_2\"")
    private String hasc2;

    @Column(name = "\"CCN_2\"")
    private Integer ccn2;

    @Column(name = "\"CCA_2\"")
    private String cca2;

    @Column(name = "\"TYPE_2\"")
    private String type2;

    @Column(name = "\"ENGTYPE_2\"")
    private String engtype2;

    @Column(name = "\"NL_NAME_2\"")
    private String nlName2;

    @Column(name = "\"VARNAME_2\"")
    private String varname2;

    @Column(name = "nom_secteur")
    private String nomSecteur;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    @Column(name = "couleur", length = 20)
    private String couleur;

    @OneToMany(mappedBy = "secteur", cascade = CascadeType.ALL)
    private List<AffectationEntity> affectations;

    @JdbcTypeCode(SqlTypes.GEOMETRY)
    @Column(columnDefinition = "geometry(MultiPolygon,4326)")
    @JsonIgnore
    private MultiPolygon geom;

    @ManyToOne
    @JoinColumn(name = "gestionnaire_id")
    @JsonIgnoreProperties({
            "affectations",
            "rapports",
            "secteurs",
            "affectationsCreees"
    })
    private UserEntity gestionnaire;
}