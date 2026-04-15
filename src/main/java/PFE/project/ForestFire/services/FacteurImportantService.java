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
    private final FacteurMapper        mapper;

    @Override
    public List<FacteurDTO> getValeursParZone(Long zoneId) {
        // ✅ Fix : utilise findByZoneId() (méthode @Query corrigée dans le repo)
        return facteurImportantRepo.findByZoneId(zoneId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FacteurImportant ajouterValeur(FacteurImportant entity) {
        return facteurImportantRepo.save(entity);
    }

    @Override
    public void supprimerValeur(Long id) {
        facteurImportantRepo.deleteById(id);
    }

    @Override
    public FacteurImportant modifierValeur(Long id, FacteurImportant facteurImportant) {
        FacteurImportant existing = facteurImportantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("FacteurImportant non trouvé : " + id));
        existing.setValeur(facteurImportant.getValeur());
        return facteurImportantRepo.save(existing);
    }
}