package PFE.project.ForestFire.interfaces;

import PFE.project.ForestFire.entities.IncendieEntity;

import java.util.Date;
import java.util.List;

public interface IncendieInterface {

    // ── CRUD ─────────────────────────────────────
    IncendieEntity saveIncendie(IncendieEntity incendie);
    List<IncendieEntity> getAllIncendies();
    IncendieEntity getIncendieById(Long id);
    IncendieEntity updateIncendie(Long id, IncendieEntity incendie);
    void deleteIncendie(Long id);

    // ── Filtres ──────────────────────────────────
    List<IncendieEntity> getByGouvernorat(String admlvl1);
    List<IncendieEntity> getByDelegation(String admlvl2);
    List<IncendieEntity> getByCountry(String country);
    List<IncendieEntity> getBySurface(Double minArea);
    List<IncendieEntity> getHistorique();
    List<IncendieEntity> getByDateBetween(Date start, Date end);

    // ── Reverse geocoding ────────────────────────
    String getGouvernoratFromCoord(Double lat, Double lng);
}