package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.IncendieDeclarationDTO;
import PFE.project.ForestFire.DTO.IncendieGeoJSONDTO;
import PFE.project.ForestFire.entities.IncendieEntity;
import PFE.project.ForestFire.interfaces.IncendieInterface;
import PFE.project.ForestFire.mapper.IncendieMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/incendies")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequiredArgsConstructor
public class IncendieController {

    private final IncendieInterface incendieInterface;

    // ── Déclaration simple ───────────────────────
    @PostMapping("/declarer")
    public ResponseEntity<IncendieGeoJSONDTO> declarer(
            @Valid @RequestBody IncendieDeclarationDTO dto) {

        IncendieEntity incendie = IncendieMapper.toEntity(dto);
        IncendieEntity saved = incendieInterface.saveIncendie(incendie);

        return ResponseEntity.ok(IncendieMapper.toDTO(saved));
    }

    // ── CRUD ─────────────────────────────────────

    @PostMapping("/ajouter")
    public ResponseEntity<IncendieGeoJSONDTO> ajouter(
            @RequestBody IncendieEntity incendie) {

        return ResponseEntity.ok(
                IncendieMapper.toDTO(incendieInterface.saveIncendie(incendie))
        );
    }

    @GetMapping("/tous")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getTous() {
        return ResponseEntity.ok(
                incendieInterface.getAllIncendies()
                        .stream()
                        .map(IncendieMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncendieGeoJSONDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                IncendieMapper.toDTO(incendieInterface.getIncendieById(id))
        );
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<IncendieGeoJSONDTO> modifier(
            @PathVariable Long id,
            @RequestBody IncendieEntity incendie) {

        return ResponseEntity.ok(
                IncendieMapper.toDTO(
                        incendieInterface.updateIncendie(id, incendie)
                )
        );
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        incendieInterface.deleteIncendie(id);
        return ResponseEntity.noContent().build();
    }

    // ── Historique ───────────────────────────────
    @GetMapping("/historique")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getHistorique() {
        return ResponseEntity.ok(
                incendieInterface.getHistorique()
                        .stream()
                        .map(IncendieMapper::toDTO)
                        .toList()
        );
    }

    // ── Filtres ──────────────────────────────────

    @GetMapping("/filtre/gouvernorat/{admlvl1}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getByGouvernorat(
            @PathVariable String admlvl1) {

        return ResponseEntity.ok(
                incendieInterface.getByGouvernorat(admlvl1)
                        .stream()
                        .map(IncendieMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/filtre/delegation/{admlvl2}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getByDelegation(
            @PathVariable String admlvl2) {

        return ResponseEntity.ok(
                incendieInterface.getByDelegation(admlvl2)
                        .stream()
                        .map(IncendieMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/filtre/pays/{country}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getByCountry(
            @PathVariable String country) {

        return ResponseEntity.ok(
                incendieInterface.getByCountry(country)
                        .stream()
                        .map(IncendieMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/filtre/surface/{minArea}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getBySurface(
            @PathVariable Double minArea) {

        return ResponseEntity.ok(
                incendieInterface.getBySurface(minArea)
                        .stream()
                        .map(IncendieMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/filtre/date")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getByDate(
            @RequestParam Date start,
            @RequestParam Date end) {

        return ResponseEntity.ok(
                incendieInterface.getByDateBetween(start, end)
                        .stream()
                        .map(IncendieMapper::toDTO)
                        .toList()
        );
    }

    // ── Reverse geocoding ────────────────────────
    @GetMapping("/gouvernorat")
    public ResponseEntity<String> getGouvernorat(
            @RequestParam Double lat,
            @RequestParam Double lng) {

        return ResponseEntity.ok(
                incendieInterface.getGouvernoratFromCoord(lat, lng)
        );
    }
}