package PFE.project.ForestFire.controller;

import PFE.project.ForestFire.DTO.IncendieGeoJSONDTO;
import PFE.project.ForestFire.entities.IncendieEntity;
import PFE.project.ForestFire.interfaces.IncendieInterface;
import PFE.project.ForestFire.mapper.IncendieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 Contrôleur REST pour gérer les incendies.
 Il permet de recevoir les requêtes HTTP (GET, POST, PUT, DELETE)
 et de retourner les données au format JSON.
*/
@RestController
@RequestMapping("/incendies")
@CrossOrigin("*")
@RequiredArgsConstructor
public class IncendieController {

    private final IncendieInterface incendieInterface;


    // Ajouter un incendie
    @PostMapping("/AjouterIncendies")
    public ResponseEntity<IncendieEntity> addIncendie(@RequestBody IncendieEntity incendie){
        IncendieEntity savedIncendie = incendieInterface.saveIncendie(incendie);
        return ResponseEntity.ok(savedIncendie);
    }


    // Afficher tous les incendies (GeoJSON)
    @GetMapping("/AfficherToutIncendie")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getAllIncendies(){

        List<IncendieEntity> incendies = incendieInterface.getAllIncendies();

        List<IncendieGeoJSONDTO> result = incendies
                .stream()
                .map(IncendieMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }


    // Afficher incendie par ID
    @GetMapping("/AfficherIncendieAvecID/{id}")
    public ResponseEntity<IncendieGeoJSONDTO> getIncendieById(@PathVariable Long id){

        IncendieEntity incendie = incendieInterface.getIncendieById(id);

        if(incendie == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(IncendieMapper.toDTO(incendie));
    }


    // Modifier incendie
    @PutMapping("/ModifierIncendie/{id}")
    public ResponseEntity<IncendieEntity> updateIncendie(@PathVariable Long id,
                                                         @RequestBody IncendieEntity incendie){

        IncendieEntity updated = incendieInterface.updateIncendie(id, incendie);
        return ResponseEntity.ok(updated);
    }


    // Supprimer incendie
    @DeleteMapping("/SupprimerIncendie/{id}")
    public ResponseEntity<Void> deleteIncendie(@PathVariable Long id){

        incendieInterface.deleteIncendie(id);

        return ResponseEntity.noContent().build();
    }


    // Filtrer par jour / nuit
    @GetMapping("/jourNuit/{dn}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getIncendiesByDn(@PathVariable String dn){

        List<IncendieEntity> incendies = incendieInterface.getIncendiesDayNight(dn);// 1. On récupère les ENTITÉS

        List<IncendieGeoJSONDTO> result = incendies
                .stream()// 2. On ouvre la liste
                .map(IncendieMapper::toDTO)// 3. ON TRANSFORME chaque Entité en DTO
                .toList();// 4. On crée une nouvelle liste de DTO

        return ResponseEntity.ok(result);
    }


    // Filtrer par date
    @GetMapping("/dateDetection/{dtDet}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getByDate(@PathVariable String dtDet){

        List<IncendieEntity> incendies = incendieInterface.getIncendiesByDate(dtDet);

        List<IncendieGeoJSONDTO> result = incendies
                .stream()
                .map(IncendieMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }


    // Filtrer par heure
    @GetMapping("/heureDetection/{hrDet}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getByHr(@PathVariable String hrDet){

        List<IncendieEntity> incendies = incendieInterface.getIncendiesByHr(hrDet);

        List<IncendieGeoJSONDTO> result = incendies
                .stream()
                .map(IncendieMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }


    // Filtrer par niveau de confiance
    @GetMapping("/ConfLevl/{conflevl}")
    public ResponseEntity<List<IncendieGeoJSONDTO>> getIncendiesByConfLvl(@PathVariable String conflevl){

        List<IncendieEntity> incendies = incendieInterface.getIncendiesByConfLvl(conflevl);

        List<IncendieGeoJSONDTO> result = incendies
                .stream()
                .map(IncendieMapper::toDTO)
                .toList();

        return ResponseEntity.ok(result);
    }

}