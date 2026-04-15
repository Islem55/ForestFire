package PFE.project.ForestFire.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncendieDeclarationDTO {

    // ── Coordonnées obligatoires ────────────────
    @NotNull(message = "La latitude est obligatoire")
    private Double latitude;

    @NotNull(message = "La longitude est obligatoire")
    private Double longitude;

    // ── Administration ─────────────────────────
    private String admlvl1; // gouvernorat
    private String admlvl2; // délégation

    // ── Informations de base ───────────────────
    private Double areaHa;
    private String country;

    // ── Source (optionnel) ─────────────────────
    private String mapSource;
}