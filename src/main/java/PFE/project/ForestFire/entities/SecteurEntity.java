package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(name = "secteur")
@Getter
@Setter
public class SecteurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_0")
    private Integer id0;

    @Column(name = "iso")
    private String iso;

    @Column(name = "nom_pay")
    private String nomPay;

    @Column(name = "id_1")
    private Integer id1;

    @Column(name = "nom_gov")
    private String nomGov;

    @Column(name = "id_2")
    private Integer id2;

    @Column(name = "nom_dele")
    private String nomDele;

    @Column(name = "hasc_2")
    private String hasc2;

    @Column(name = "ccn_2")
    private Integer ccn2;

    @Column(name = "cca_2")
    private String cca2;

    @Column(name = "type_2")
    private String type2;

    @Column(name = "engtype_2")
    private String engtype2;

    @Column(name = "nl_name_2")
    private String nlName2;

    @Column(name = "varname_2")
    private String varname2;

    @Column(name = "nom_secteur")
    private String nomSecteur;

    @JdbcTypeCode(SqlTypes.GEOMETRY)
    @Column(columnDefinition = "geometry(MultiPolygon,4326)")
    private MultiPolygon geom;
}