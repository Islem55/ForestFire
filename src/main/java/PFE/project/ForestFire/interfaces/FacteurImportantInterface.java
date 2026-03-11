package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.DTO.FacteurDTO;
import PFE.project.ForestFire.entities.FacteurImportant;

import java.util.List;

public interface FacteurImportantInterface {

    List<FacteurDTO> getValeursParZone(Long zoneId);

    FacteurImportant ajouterValeur(FacteurImportant entity);

    void supprimerValeur(Long id);

    FacteurImportant modifierValeur(Long id, FacteurImportant facteurImportant);



}
