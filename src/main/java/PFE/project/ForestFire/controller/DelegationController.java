package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.DelegationGeoJSONDTO;
import PFE.project.ForestFire.entities.DelegationEntity;
import PFE.project.ForestFire.interfaces.DelegationInterface;
import PFE.project.ForestFire.mapper.DelegationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delegation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DelegationController {

    private final DelegationInterface delegationInterface;

    public DelegationController(DelegationInterface delegationInterface) {
        this.delegationInterface = delegationInterface;
    }

    // ✅ Liste des gouvernorats pour le formulaire
    @GetMapping("/gouvernorats")
    public ResponseEntity<List<String>> getGouvernorats() {
        return ResponseEntity.ok(
                delegationInterface.getAllGouvernorats());
    }

    // ✅ GeoJSON toutes les délégations (pour la carte)
    @GetMapping("/geojson")
    public ResponseEntity<List<DelegationGeoJSONDTO>> getAllGeoJSON() {
        return ResponseEntity.ok(
                delegationInterface.getAllDelegations().stream()
                        .map(DelegationMapper::toDTO).toList());
    }

    // ✅ GeoJSON d'un gouvernorat spécifique
    @GetMapping("/geojson/{nomGov}")
    public ResponseEntity<List<DelegationGeoJSONDTO>> getByGouv(
            @PathVariable String nomGov) {
        return ResponseEntity.ok(
                delegationInterface.getDelegationsByGouvernorat(nomGov)
                        .stream().map(DelegationMapper::toDTO).toList());
    }
}