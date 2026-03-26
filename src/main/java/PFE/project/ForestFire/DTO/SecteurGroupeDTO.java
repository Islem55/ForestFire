package PFE.project.ForestFire.DTO;

import lombok.Data;
import java.util.List;

@Data  // ✅ génère TOUS les getters/setters automatiquement
public class SecteurGroupeDTO {

    // nomSecteur = clé principale d'affichage
    private String nomSecteur;

    // Premier ID technique (pour modifier)
    private Long id;

    // ✅ Tous les IDs du secteur (pour supprimer toutes les lignes)
    private List<Long> ids;

    // Description
    private String description;

    // ✅ Tous les gouvernorats uniques de ce secteur
    private List<String> gouvernorats;

    // ✅ Nombre de gouvernorats
    private int nombreGouvernorats;
}