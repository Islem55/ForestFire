package PFE.project.ForestFire.services;

import PFE.project.ForestFire.DTO.FacteurDTO;
import PFE.project.ForestFire.entities.FacteurImportant;
import PFE.project.ForestFire.interfaces.FacteurImportantInterface;
import PFE.project.ForestFire.mapper.FacteurMapper;
import PFE.project.ForestFire.repository.FacteurImportantRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacteurImportantService implements FacteurImportantInterface {

    private final FacteurImportantRepo facteurImportantRepo;
    private final FacteurMapper mapper;

    public List<FacteurDTO> getValeursParZone(Long zoneId) {
        return facteurImportantRepo.findByZoneForestiereEntity_Id(zoneId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public FacteurImportant ajouterValeur(FacteurImportant entity) {
        return facteurImportantRepo.save(entity);
    }

    public void supprimerValeur(Long id) {
        facteurImportantRepo.deleteById(id);
    }

    public FacteurImportant modifierValeur(Long id, FacteurImportant facteurImportant) {
        FacteurImportant existing = facteurImportantRepo.findById(id).orElseThrow();
        existing.setValeur(facteurImportant.getValeur());
        return facteurImportantRepo.save(existing);
    }
}