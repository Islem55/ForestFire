package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.DelegationGeoJSONDTO;
import PFE.project.ForestFire.entities.DelegationEntity;
import PFE.project.ForestFire.entities.SecteurEntity;
import PFE.project.ForestFire.interfaces.DelegationInterface;
import PFE.project.ForestFire.interfaces.SecteurInterface;
import PFE.project.ForestFire.mapper.DelegationMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delegation")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class DelegationController {

    private final DelegationInterface delegationInterface;
    private final SecteurInterface  secteurInterface;

    public DelegationController(DelegationInterface delegationInterface,
                                SecteurInterface secteurInterface) {
        this.delegationInterface = delegationInterface;
        this.secteurInterface = secteurInterface;
    }

    //  Liste des gouvernorats pour le formulaire
    @GetMapping("/gouvernorats")
    public ResponseEntity<List<String>> getGouvernorats() {
        return ResponseEntity.ok(
                delegationInterface.getAllGouvernorats());
    }

    // ✅ GeoJSON toutes les délégations (pour la carte)
    @GetMapping("/geojson")
    public ResponseEntity<Map<String, Object>> getAllGeoJSON() {

        List<Map<String, Object>> features = delegationInterface.getAllDelegations()
                .stream()
                .map(d -> {
                    Map<String, Object> feature = new java.util.HashMap<>();

                    feature.put("type", "Feature");

                    Map<String, Object> props = new java.util.HashMap<>();
                    props.put("Nom_dele", d.getDelegNa1());
                    props.put("Nom_gov", d.getNomGov());

                    feature.put("properties", props);

                    // ⚠️ IMPORTANT : géométrie depuis DTO
                    feature.put("geometry", d.getGeom());

                    return feature;
                })
                .toList();

        Map<String, Object> geojson = new java.util.HashMap<>();
        geojson.put("type", "FeatureCollection");
        geojson.put("features", features);

        return ResponseEntity.ok(geojson);
    }

    // ✅ GeoJSON d'un gouvernorat spécifique
    @GetMapping("/geojson/{nomGov}")
    public ResponseEntity<Map<String, Object>> getByGouv(
            @PathVariable String nomGov) {

        List<Map<String, Object>> features = delegationInterface
                .getDelegationsByGouvernorat(nomGov)
                .stream()
                .map(d -> {
                    Map<String, Object> feature = new java.util.HashMap<>();

                    feature.put("type", "Feature");

                    Map<String, Object> props = new java.util.HashMap<>();
                    props.put("Nom_dele", d.getDelegNa1());
                    props.put("Nom_gov", d.getNomGov());

                    feature.put("properties", props);
                    feature.put("geometry", d.getGeom());

                    return feature;
                })
                .toList();

        Map<String, Object> geojson = new java.util.HashMap<>();
        geojson.put("type", "FeatureCollection");
        geojson.put("features", features);

        return ResponseEntity.ok(geojson);
    }







    @PostMapping("/Ajouter")
    public ResponseEntity<DelegationEntity> ajouter(@RequestBody DelegationEntity zone) {
        return ResponseEntity.ok(delegationInterface.saveZone(zone));
    }

    @GetMapping("/AfficherTout")
    public ResponseEntity<List<DelegationGeoJSONDTO>> afficherTout() {
        List<DelegationGeoJSONDTO> zones = delegationInterface.getAllZones()
                .stream()
                .map(DelegationMapper::toDTO)
                .toList();
        return ResponseEntity.ok(zones);
    }

    @GetMapping("Afficher/{id}")
    public ResponseEntity<DelegationGeoJSONDTO> afficherParId(@PathVariable Long id) {
        DelegationEntity zone = delegationInterface.getZoneById(id);
        return (zone != null) ? ResponseEntity.ok(DelegationMapper.toDTO(zone)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/Supprimer/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        delegationInterface.deleteZone(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<DelegationGeoJSONDTO>> getAll() {
        return ResponseEntity.ok(
                delegationInterface.getAll().stream()
                        .map(z -> {
                            DelegationGeoJSONDTO dto = new DelegationGeoJSONDTO();
                            dto.setId(z.getId());
                            dto.setDelegNa1(z.getDelegNa1());
                            dto.setNomGov(z.getNomGov());
                            System.out.println("Zone: id=" + z.getId() + " nom=" + z.getDelegNa1());

                            return dto;
                        })
                        .toList()
        );
    }

    //  Récupérer une zone par id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        DelegationEntity zone = delegationInterface.getById(id);
        if (zone == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(zone);
    }


    // ✅ Gouvernorats du secteur du gestionnaire
    @GetMapping("/gouvernorats-par-gestionnaire/{gestionnaireId}")
    public ResponseEntity<?> gouvernoratsByGestionnaire(
            @PathVariable Long gestionnaireId) {

        // ✅ this.secteurInterface — pas SecteurInterface statique
        SecteurEntity secteur = this.secteurInterface.getAllSecteurs()
                .stream()
                .filter(s -> s.getGestionnaire() != null &&
                        s.getGestionnaire().getId().equals(gestionnaireId))
                .findFirst()
                .orElse(null);

        if (secteur == null)
            return ResponseEntity.badRequest()
                    .body("Aucun secteur affecté à ce gestionnaire !");

        List<String> gouvernorats =
                delegationInterface.getGouvernoratsBySecteurId(secteur.getId());

        return ResponseEntity.ok(gouvernorats);
    }


    // ✅ Délégations du secteur du gestionnaire
    @GetMapping("/delegations-par-gestionnaire/{gestionnaireId}")
    public ResponseEntity<?> delegationsByGestionnaire(
            @PathVariable Long gestionnaireId) {

        // ✅ this.secteurInterface — pas SecteurInterface statique
        SecteurEntity secteur = this.secteurInterface.getAllSecteurs()
                .stream()
                .filter(s -> s.getGestionnaire() != null &&
                        s.getGestionnaire().getId().equals(gestionnaireId))
                .findFirst()
                .orElse(null);

        if (secteur == null)
            return ResponseEntity.badRequest()
                    .body("Aucun secteur affecté à ce gestionnaire !");

        return ResponseEntity.ok(
                delegationInterface.getBySecteurId(secteur.getId())
                        .stream()
                        .map(d -> Map.of(
                                "id", d.getId(),
                                "delegNa1", d.getDelegNa1() != null ? d.getDelegNa1() : "",
                                "nomGov", d.getNomGov() != null ? d.getNomGov() : ""
                        ))
                        .toList()
        );
    }




    @GetMapping("/geojson/secteur/{secteurId}/gouvernorat/{nomGov}")
    public ResponseEntity<Map<String, Object>> getBySecteurAndGov(
            @PathVariable Long secteurId,
            @PathVariable String nomGov) {

        List<Map<String, Object>> features =
                delegationInterface.getBySecteurId(secteurId).stream()
                        .filter(d -> nomGov.equalsIgnoreCase(d.getNomGov()))
                        .map(d -> {
                            Map<String, Object> feature = new java.util.HashMap<>();

                            feature.put("type", "Feature");

                            Map<String, Object> props = new java.util.HashMap<>();
                            props.put("Nom_dele", d.getDelegNa1());
                            props.put("Nom_gov", d.getNomGov());

                            feature.put("properties", props);
                            feature.put("geometry", d.getGeom());

                            return feature;
                        })
                        .toList();

        Map<String, Object> geojson = new java.util.HashMap<>();
        geojson.put("type", "FeatureCollection");
        geojson.put("features", features);

        return ResponseEntity.ok(geojson);
    }



}