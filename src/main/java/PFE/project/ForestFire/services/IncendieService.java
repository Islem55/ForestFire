package PFE.project.ForestFire.services;

import PFE.project.ForestFire.entities.IncendieEntity;
import PFE.project.ForestFire.interfaces.IncendieInterface;
import PFE.project.ForestFire.repository.IncendieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IncendieService implements IncendieInterface {

    private final IncendieRepo incendieRepository;

    // ── CRUD ─────────────────────────────────────

    @Override
    public IncendieEntity saveIncendie(IncendieEntity incendie) {
        return incendieRepository.save(incendie);
    }

    @Override
    public List<IncendieEntity> getAllIncendies() {
        return incendieRepository.findAll();
    }

    @Override
    public IncendieEntity getIncendieById(Long id) {
        return incendieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incendie non trouvé : " + id));
    }

    @Override
    public IncendieEntity updateIncendie(Long id, IncendieEntity incendie) {
        IncendieEntity existing = getIncendieById(id);

        existing.setLatitude(incendie.getLatitude());
        existing.setLongitude(incendie.getLongitude());

        existing.setInitialDate(incendie.getInitialDate());
        existing.setFinalDate(incendie.getFinalDate());
        existing.setUpdated(incendie.getUpdated());

        existing.setAreaHa(incendie.getAreaHa());

        existing.setIso2(incendie.getIso2());
        existing.setIso3(incendie.getIso3());
        existing.setCountry(incendie.getCountry());

        existing.setAdmlvl1(incendie.getAdmlvl1());
        existing.setAdmlvl2(incendie.getAdmlvl2());
        existing.setAdmlvl3(incendie.getAdmlvl3());
        existing.setAdmlvl5(incendie.getAdmlvl5());

        existing.setMapSource(incendie.getMapSource());

        existing.setBroadleave(incendie.getBroadleave());
        existing.setConiferous(incendie.getConiferous());
        existing.setMixedFore(incendie.getMixedFore());
        existing.setSclerophil(incendie.getSclerophil());
        existing.setTransition(incendie.getTransition());
        existing.setOtherNatu(incendie.getOtherNatu());
        existing.setAgricultur(incendie.getAgricultur());
        existing.setArtificial(incendie.getArtificial());
        existing.setOtherPerc(incendie.getOtherPerc());
        existing.setNatura2kP(incendie.getNatura2kP());

        existing.setAreaCode(incendie.getAreaCode());
        existing.setEuArea(incendie.getEuArea());

        return incendieRepository.save(existing);
    }

    @Override
    public void deleteIncendie(Long id) {
        incendieRepository.deleteById(id);
    }

    // ── Filtres ──────────────────────────────────

    @Override
    public List<IncendieEntity> getByGouvernorat(String admlvl1) {
        return incendieRepository.findByAdmlvl1(admlvl1);
    }

    @Override
    public List<IncendieEntity> getByDelegation(String admlvl2) {
        return incendieRepository.findByAdmlvl2(admlvl2);
    }

    @Override
    public List<IncendieEntity> getByCountry(String country) {
        return incendieRepository.findByCountry(country);
    }

    @Override
    public List<IncendieEntity> getBySurface(Double minArea) {
        return incendieRepository.findByAreaHaGreaterThan(minArea);
    }

    @Override
    public List<IncendieEntity> getHistorique() {
        return incendieRepository.findAllByOrderByInitialDateDesc();
    }

    @Override
    public List<IncendieEntity> getByDateBetween(Date start, Date end) {
        return incendieRepository.findByInitialDateBetween(start, end);
    }

    // ── Reverse Geocoding ────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public String getGouvernoratFromCoord(Double lat, Double lng) {
        try {
            String url = String.format(
                    "https://nominatim.openstreetmap.org/reverse?lat=%s&lon=%s&format=json",
                    lat, lng
            );

            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("address")) {
                Map<String, String> address = (Map<String, String>) response.get("address");

                if (address.containsKey("state")) return address.get("state");
                if (address.containsKey("county")) return address.get("county");
            }

        } catch (Exception e) {
            return "Inconnu";
        }

        return "Inconnu";
    }
}