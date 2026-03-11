package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "facteur_important")
@Data
public class FacteurImportant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valeur; // La valeur extraite du raster (ex: moyenne)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_forestiere_id")
    private ZoneForestiereEntity zoneForestiereEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facteur_id")
    private FacteurEntity facteurEntity;
}