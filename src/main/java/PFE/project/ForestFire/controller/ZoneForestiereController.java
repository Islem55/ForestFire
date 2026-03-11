package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.ZoneForestiereDTO;
import PFE.project.ForestFire.entities.ZoneForestiereEntity;
import PFE.project.ForestFire.interfaces.ZoneForestiereInterface;
import PFE.project.ForestFire.mapper.ZoneForestiereMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zones-forestieres")
@CrossOrigin("*")
public class ZoneForestiereController {

    private final ZoneForestiereInterface zoneForestiereInterface;

    public ZoneForestiereController(ZoneForestiereInterface zoneInterface) {
        this.zoneForestiereInterface = zoneInterface;
    }

    @PostMapping("/Ajouter")
    public ResponseEntity<ZoneForestiereEntity> ajouter(@RequestBody ZoneForestiereEntity zone) {
        return ResponseEntity.ok(zoneForestiereInterface.saveZone(zone));
    }

    @GetMapping("/AfficherTout")
    public ResponseEntity<List<ZoneForestiereDTO>> afficherTout() {
        List<ZoneForestiereDTO> zones = zoneForestiereInterface.getAllZones()
                .stream()
                .map(ZoneForestiereMapper::toDTO)
                .toList();
        return ResponseEntity.ok(zones);
    }

    @GetMapping("Afficher/{id}")
    public ResponseEntity<ZoneForestiereDTO> afficherParId(@PathVariable Long id) {
        ZoneForestiereEntity zone = zoneForestiereInterface.getZoneById(id);
        return (zone != null) ? ResponseEntity.ok(ZoneForestiereMapper.toDTO(zone)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        zoneForestiereInterface.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}