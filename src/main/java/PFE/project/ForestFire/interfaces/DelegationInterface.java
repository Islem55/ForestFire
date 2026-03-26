package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.DelegationEntity;
import java.util.List;

public interface DelegationInterface {
    List<String> getAllGouvernorats();
    List<DelegationEntity> getDelegationsByGouvernorat(String nomGov);
    List<DelegationEntity> getAllDelegations();
}