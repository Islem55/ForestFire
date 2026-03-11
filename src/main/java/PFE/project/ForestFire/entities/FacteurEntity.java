package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;
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

    @Enumerated(EnumType.STRING)
    private TypeFacteur typeFacteur; // TOPOGRAPHIQUE, BIOCLIMATIQUE, PROXIMITE

    private String unite; // ex: 'degré', 'mètre'

    @OneToMany(mappedBy = "facteurEntity", cascade = CascadeType.ALL)
    private List<FacteurImportant> facteurImportants;
}