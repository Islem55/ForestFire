package PFE.project.ForestFire.interfaces;
import PFE.project.ForestFire.entities.ZoneForestiereEntity;

import java.util.List;
public interface ZoneForestiereInterface {
    ZoneForestiereEntity saveZone(ZoneForestiereEntity zone);
    List<ZoneForestiereEntity> getAllZones();
    ZoneForestiereEntity getZoneById(Long id);
    void deleteZone(Long id);
}
