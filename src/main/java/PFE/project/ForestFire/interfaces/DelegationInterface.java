package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.DelegationEntity;

import java.util.List;

public interface DelegationInterface {
    List<String> getAllGouvernorats();
    List<DelegationEntity> getDelegationsByGouvernorat(String nomGov);
    List<DelegationEntity> getAllDelegations();

    DelegationEntity saveZone(DelegationEntity zone);
    List<DelegationEntity> getAllZones();
    DelegationEntity getZoneById(Long id);
    void deleteZone(Long id);
    DelegationEntity getById(Long id);
    List<DelegationEntity> getAll();
    DelegationEntity getByNomGov(String nomGov);
    // ✅ Délégations par secteur du gestionnaire
    List<String> getAllGouvernorat();
    List<DelegationEntity> getDelegationsByGouvernorats(String nomGov);
    // ✅ Ajouter ces deux méthodes
    List<String> getGouvernoratsBySecteurId(Long secteurId);
    List<DelegationEntity> getBySecteurId(Long secteurId);

    DelegationEntity getByNomGovAndGestionnaire(String nomGov, Long gestionnaireId);

}