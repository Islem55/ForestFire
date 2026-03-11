package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.ZoneForestiereEntity;
import PFE.project.ForestFire.interfaces.ZoneForestiereInterface;
import PFE.project.ForestFire.repository.ZoneForestiereRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ZoneForestiereService implements ZoneForestiereInterface {

    private final ZoneForestiereRepo zoneForestiereRepo;

    public ZoneForestiereService(ZoneForestiereRepo zoneForestiereRepo) {
        this.zoneForestiereRepo = zoneForestiereRepo;
    }

    @Override
    public ZoneForestiereEntity saveZone(ZoneForestiereEntity zone) {
        return zoneForestiereRepo.save(zone);
    }

    @Override
    public List<ZoneForestiereEntity> getAllZones() {
        return zoneForestiereRepo.findAll();
    }

    @Override
    public ZoneForestiereEntity getZoneById(Long id) {
        return zoneForestiereRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteZone(Long id) {
        zoneForestiereRepo.deleteById(id);
    }
}