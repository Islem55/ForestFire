package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.IncendieEntity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface IncendieInterface {

    IncendieEntity saveIncendie (IncendieEntity incendie);

    List<IncendieEntity> getAllIncendies();

    IncendieEntity getIncendieById(Long id);

    void deleteIncendie(Long id);

    IncendieEntity updateIncendie(Long id,IncendieEntity incendie);

    List<IncendieEntity> getIncendiesDayNight(String dayNight);

    List<IncendieEntity> getIncendiesByConfLvl(String confLvl);

    List<IncendieEntity> getIncendiesByHr(String hrDet);

    List<IncendieEntity> getIncendiesByDate(String dtDet);

}
