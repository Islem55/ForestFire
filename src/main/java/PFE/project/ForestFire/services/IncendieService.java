package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.IncendieEntity;
import PFE.project.ForestFire.interfaces.IncendieInterface;
import PFE.project.ForestFire.repository.IncendieRepo;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class IncendieService implements IncendieInterface {

    private final IncendieRepo incendieRepo;

    @Override
    // Méthode qui permet d'enregistrer un incendie dans la base de données
    public IncendieEntity saveIncendie(IncendieEntity incendie){

        // Création d'un objet GeometryFactory utilisé pour créer des objets géométriques (Point, Line, Polygon)
        GeometryFactory geometryFactory = new GeometryFactory();

        // Création d'un point géographique à partir de la longitude (X) et de la latitude (Y)
        // Coordinate(longitude, latitude) est le format utilisé par les systèmes géographiques
        Point point = geometryFactory.createPoint(
                new Coordinate(incendie.getLongitude(), incendie.getLatitude())
        );

        // Définition du système de coordonnées spatial (SRID)
        // 4326 correspond au système WGS84 utilisé par les GPS et les cartes web
        point.setSRID(4326);

        // Affectation du point géographique créé au champ "geom" de l'entité Incendie
        // Cela représente la localisation de l'incendie dans la base de données
        incendie.setGeom(point);

        // Sauvegarde de l'entité incendie dans la base de données via le repository Spring Data JPA
        return incendieRepo.save(incendie);
    }

    @Override
        /*
    récupérer tous les incendies
     */
    public List<IncendieEntity> getAllIncendies(){
        return incendieRepo.findAll();
    }

    @Override
    public IncendieEntity getIncendieById(Long id){
        return incendieRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteIncendie(Long id ){
        incendieRepo.deleteById(id);
    }

    @Override
    public IncendieEntity updateIncendie(Long id ,IncendieEntity  incendie){
        IncendieEntity existing =incendieRepo.findById(id).orElseThrow();

        existing.setBrightness(incendie.getBrightness());
        existing.setFrp(incendie.getFrp());
        existing.setTempT31(incendie.getTempT31());
        existing.setType(incendie.getType());
        existing.setConfLvl(incendie.getConfLvl());
        return incendieRepo.save(existing);
    }



    @Override
    public List<IncendieEntity> getIncendiesDayNight(String dayNight){
        return  incendieRepo.findByDayNight(dayNight);
    }

    @Override
    public List<IncendieEntity> getIncendiesByConfLvl(String confLvl){
        return incendieRepo.findByConfLvl(confLvl);
    }


    @Override
    public List<IncendieEntity> getIncendiesByDate(String dtDet){
        return incendieRepo.findByDtDet(dtDet);
    }

    @Override
    public List<IncendieEntity> getIncendiesByHr(String hrDet){
        return  incendieRepo.findByHrDet(hrDet);
    }
}
