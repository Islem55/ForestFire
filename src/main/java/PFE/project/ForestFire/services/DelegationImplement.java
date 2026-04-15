package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.DelegationEntity;
import PFE.project.ForestFire.interfaces.DelegationInterface;
import PFE.project.ForestFire.repository.DelegationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DelegationImplement implements DelegationInterface {

    private final DelegationRepo delegationRepo;

    @Override
    public List<String> getAllGouvernorats() {
        return delegationRepo.findDistinctNomGov();
    }

    @Override
    public List<DelegationEntity> getDelegationsByGouvernorat(
            String nomGov) {
        return delegationRepo.findByNomGov(nomGov);
    }

    @Override
    public List<DelegationEntity> getAllDelegations() {
        return delegationRepo.findAll();
    }








    @Override
    public DelegationEntity saveZone(DelegationEntity zone) {
        return delegationRepo.save(zone);
    }

    @Override
    public List<DelegationEntity> getAllZones() {
        return delegationRepo.findAll();
    }



    @Override
    public void deleteZone(Long id) {
        delegationRepo.deleteById(id);
    }

    @Override
    public DelegationEntity getById(Long id) {
        return delegationRepo.findById(id).orElse(null);
    }

    @Override
    public List<DelegationEntity> getAll() {
        return delegationRepo.findAll();
    }


    @Override
    public DelegationEntity getZoneById(Long id) {
        return delegationRepo.findById(id).orElse(null);
    }





    @Override
    public List<String> getAllGouvernorat() {
        return delegationRepo.findDistinctGouvernorats();
    }

    @Override
    public List<DelegationEntity> getDelegationsByGouvernorats(String nomGov) {
        return delegationRepo.findAll().stream()
                .filter(d -> nomGov.equals(d.getNomGov()))
                .toList();
    }
    @Override
    public List<String> getGouvernoratsBySecteurId(Long secteurId) {
        return delegationRepo.findGouvernoratsBySecteurId(secteurId);
    }

    @Override
    public List<DelegationEntity> getBySecteurId(Long secteurId) {
        return delegationRepo.findBySecteurId(secteurId);
    }
    @Override
    public DelegationEntity getByNomGov(String nomGov) {
        System.out.println("🔍 Recherche délégation pour nomGov: " + nomGov);
        DelegationEntity result = delegationRepo
                .findFirstByNomGov(nomGov).orElse(null);
        System.out.println("Résultat: " + (result != null ? result.getId() : "NULL ❌"));
        return result;
    }


    @Override
    public DelegationEntity getByNomGovAndGestionnaire(String nomGov, Long gestionnaireId) {
        System.out.println("=== getByNomGovAndGestionnaire DEBUG ===");
        System.out.println("📥 nomGov reçu: '" + nomGov + "'");
        System.out.println("📥 gestionnaireId reçu: " + gestionnaireId);

        if (nomGov == null || nomGov.trim().isEmpty()) {
            System.out.println("❌ nomGov est NULL ou vide !");
            return null;
        }

        System.out.println("🔍 Recherche par findFirstByNomGov...");
        DelegationEntity result = delegationRepo
                .findFirstByNomGov(nomGov.trim()).orElse(null);

        if (result == null) {
            System.out.println("❌ Aucune délégation trouvée pour nomGov='" + nomGov.trim() + "'");
        } else {
            System.out.println("✅ Délégation trouvée:");
            System.out.println("   ID: " + result.getId());
            System.out.println("   Nom_gov: " + result.getNomGov());
            System.out.println("   DelegNa1: " + result.getDelegNa1());
        }

        System.out.println("=== getByNomGovAndGestionnaire DEBUG END ===");
        return result;
    }


}