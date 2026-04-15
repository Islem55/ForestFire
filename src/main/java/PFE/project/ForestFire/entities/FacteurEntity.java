package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facteur")
@Data
public class FacteurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // ex: 'SLOPE', 'ROAD_DIST'

    private String nom; // ex: 'Pente du terrain'

    //  Fix 2 : suppression de "private String Type" qui causait un conflit
    //    avec le mot réservé SQL "type" et doublonnait typeFacteur
    @Enumerated(EnumType.STRING)
    private TypeFacteur typeFacteur; // TOPOGRAPHIQUE, BIOCLIMATIQUE, PROXIMITE

    private String unite; // ex: 'degré', 'mètre'

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    @OneToMany(mappedBy = "facteurEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacteurImportant> facteurImportants;
}