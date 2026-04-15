package PFE.project.ForestFire.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneGroupeDTO {
    private Long id;
    private List<Long> ids;
    private String nomZone;
    private String nomGov;
    private String nomSecteur;
    private String couleur;
    private String typeZone;
    private List<String> delegations;
    private int nombreDelegations;
}