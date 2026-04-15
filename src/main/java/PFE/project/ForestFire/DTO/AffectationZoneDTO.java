package PFE.project.ForestFire.DTO;

public class AffectationZoneDTO {

    private Long affectationId;
    private Long zoneId;
    private String nomZone;
    private String nomGov;

    private Long forestierId;
    private String forestierNom;
    private String forestierPrenom;
    private String forestierEmail;

    private Long gestionnaireId;
    private String gestionnaireNom;
    private String gestionnairePrenom;

    private String dateAffectation;

    // ✅ Constructeur
    public AffectationZoneDTO(Long affectationId, Long zoneId, String nomZone,
                              String nomGov,
                              Long forestierId, String forestierNom,
                              String forestierPrenom, String forestierEmail,
                              Long gestionnaireId, String gestionnaireNom,
                              String gestionnairePrenom, String dateAffectation) {

        this.affectationId = affectationId;
        this.zoneId = zoneId;
        this.nomZone = nomZone;
        this.nomGov = nomGov;
        this.forestierId = forestierId;
        this.forestierNom = forestierNom;
        this.forestierPrenom = forestierPrenom;
        this.forestierEmail = forestierEmail;
        this.gestionnaireId = gestionnaireId;
        this.gestionnaireNom = gestionnaireNom;
        this.gestionnairePrenom = gestionnairePrenom;
        this.dateAffectation = dateAffectation;
    }

    // ✅ GETTERS
    public Long getAffectationId() { return affectationId; }
    public Long getZoneId() { return zoneId; }
    public String getNomZone() { return nomZone; }
    public String getNomGov() { return nomGov; }
    public Long getForestierId() { return forestierId; }
    public String getForestierNom() { return forestierNom; }
    public String getForestierPrenom() { return forestierPrenom; }
    public String getForestierEmail() { return forestierEmail; }
    public Long getGestionnaireId() { return gestionnaireId; }
    public String getGestionnaireNom() { return gestionnaireNom; }
    public String getGestionnairePrenom() { return gestionnairePrenom; }
    public String getDateAffectation() { return dateAffectation; }

    // ✅ SETTERS (IMPORTANT)
    public void setAffectationId(Long affectationId) { this.affectationId = affectationId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }
    public void setNomZone(String nomZone) { this.nomZone = nomZone; }
    public void setNomGov(String nomGov) { this.nomGov = nomGov; }
    public void setForestierId(Long forestierId) { this.forestierId = forestierId; }
    public void setForestierNom(String forestierNom) { this.forestierNom = forestierNom; }
    public void setForestierPrenom(String forestierPrenom) { this.forestierPrenom = forestierPrenom; }
    public void setForestierEmail(String forestierEmail) { this.forestierEmail = forestierEmail; }
    public void setGestionnaireId(Long gestionnaireId) { this.gestionnaireId = gestionnaireId; }
    public void setGestionnaireNom(String gestionnaireNom) { this.gestionnaireNom = gestionnaireNom; }
    public void setGestionnairePrenom(String gestionnairePrenom) { this.gestionnairePrenom = gestionnairePrenom; }
    public void setDateAffectation(String dateAffectation) { this.dateAffectation = dateAffectation; }
}