package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.MultiPolygon;

@Entity
@Table(name = "Zone_Forestier")
@Data
public class ZoneForestiereEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_0;

    private String id;
    private String hasc_2;
    private String circo_id;

    // Si la colonne SQL est "Nom_deleg", la variable Java doit être exactement "Nom_deleg"
    private String Nom_deleg;
    private String deleg_na_1;
    private String circo_name;
    private String cico_na_1;
    private String gov_name_a;
    private String Nom_gov;
    private String adm_id;

    private Long deleg_id;
    private Long gov_id;

    // Hibernate Spatial fera le lien automatiquement avec la colonne "geom"
    private MultiPolygon geom;

    // --- RELATIONS ---

    @ManyToOne
    @JoinColumn(name = "secteur_id")
    private SecteurEntity secteurEntity;



    // Lien vers les résultats de risque
    //@OneToMany(mappedBy = "zoneForestiere")
    //private List<ResultatRisqueEntity> historiqueRisqueEntity;
}
