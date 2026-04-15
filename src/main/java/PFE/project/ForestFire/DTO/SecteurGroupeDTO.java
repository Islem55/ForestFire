package PFE.project.ForestFire.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecteurGroupeDTO {

    private Long id;
    private List<Long> ids;
    private String nomSecteur;
    private List<String> gouvernorats;
    private List<String> delegations;       // ✅ délégations
    private int nombreGouvernorats;
    private int nombreDelegations;          // ✅ nombre délégations
    private String description;
    private String couleur;
}