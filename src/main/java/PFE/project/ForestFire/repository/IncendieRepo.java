package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.IncendieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface IncendieRepo extends JpaRepository<IncendieEntity,Long> {

    List<IncendieEntity> findByConfLvl(String confLvl);

    List<IncendieEntity> findByDayNight(String dayNight);

    List<IncendieEntity> findByDtDet(String dtDet);

    List<IncendieEntity> findByHrDet(String hrDet);

}
