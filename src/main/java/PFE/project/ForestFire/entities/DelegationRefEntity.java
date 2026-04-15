package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delegation_ref")
@Getter
@Setter
@NoArgsConstructor
public class DelegationRefEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_dele")
    private String nomDele;

    @Column(name = "nom_gov")
    private String nomGov;

    @Column(name = "nom_secteur")
    private String nomSecteur;
}