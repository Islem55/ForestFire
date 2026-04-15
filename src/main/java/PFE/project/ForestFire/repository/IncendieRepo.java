package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.IncendieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IncendieRepo extends JpaRepository<IncendieEntity, Long> {

    // ── Filtrage par dates ───────────────────────
    List<IncendieEntity> findByInitialDate(Date initialDate);
    List<IncendieEntity> findByFinalDate(Date finalDate);
    List<IncendieEntity> findByInitialDateBetween(Date start, Date end);
    // ── Filtrage administratif ───────────────────
    List<IncendieEntity> findByAdmlvl1(String admlvl1); // gouvernorat
    List<IncendieEntity> findByAdmlvl2(String admlvl2); // délégation

    // ── Filtrage pays ────────────────────────────
    List<IncendieEntity> findByCountry(String country);

    // ── Filtrage surface ─────────────────────────
    List<IncendieEntity> findByAreaHaGreaterThan(Double areaHa);

    // ── Filtrage source carte ────────────────────
    List<IncendieEntity> findByMapSource(String mapSource);

    // ── Tri par date (historique) ────────────────
    List<IncendieEntity> findAllByOrderByInitialDateDesc();
}