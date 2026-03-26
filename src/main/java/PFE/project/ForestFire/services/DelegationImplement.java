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
}