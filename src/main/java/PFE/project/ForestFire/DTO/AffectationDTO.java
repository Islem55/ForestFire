package PFE.project.ForestFire.DTO;

public class AffectationDTO {

    private Long secteurId;
    private String nomSecteur;

    private Long gestionnaireId;
    private String gestionnaireNom;
    private String gestionnairePrenom;
    private String gestionnaireEmail;

    //  Constructeur complet
    public AffectationDTO(Long secteurId, String nomSecteur,
                          Long gestionnaireId,
                          String gestionnaireNom, String gestionnairePrenom,
                          String gestionnaireEmail) {
        this.secteurId = secteurId;
        this.nomSecteur = nomSecteur;

        this.gestionnaireId = gestionnaireId;
        this.gestionnaireNom = gestionnaireNom;
        this.gestionnairePrenom = gestionnairePrenom;
        this.gestionnaireEmail = gestionnaireEmail;
    }

    // ✅ Constructeur vide
    public AffectationDTO() {}

    // ✅ Getters & Setters
    public Long getSecteurId() { return secteurId; }
    public void setSecteurId(Long secteurId) { this.secteurId = secteurId; }

    public String getNomSecteur() { return nomSecteur; }
    public void setNomSecteur(String nomSecteur) { this.nomSecteur = nomSecteur; }



    public Long getGestionnaireId() { return gestionnaireId; }
    public void setGestionnaireId(Long gestionnaireId) { this.gestionnaireId = gestionnaireId; }

    public String getGestionnaireNom() { return gestionnaireNom; }
    public void setGestionnaireNom(String gestionnaireNom) { this.gestionnaireNom = gestionnaireNom; }

    public String getGestionnairePrenom() { return gestionnairePrenom; }
    public void setGestionnairePrenom(String gestionnairePrenom) { this.gestionnairePrenom = gestionnairePrenom; }

    public String getGestionnaireEmail() { return gestionnaireEmail; }
    public void setGestionnaireEmail(String gestionnaireEmail) { this.gestionnaireEmail = gestionnaireEmail; }
}