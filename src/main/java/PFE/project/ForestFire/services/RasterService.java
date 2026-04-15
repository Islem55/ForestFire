package PFE.project.ForestFire.services;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.util.factory.Hints;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Service d'extraction de valeurs depuis un fichier GeoTIFF.
 * Calcule la moyenne de toutes les cellules non-NoData du raster.
 */
@Service
public class RasterService {

    // Valeur NoData standard pour les rasters (pixels vides/invalides)
    private static final double NO_DATA_VALUE = -9999.0;

    /**
     * Extrait la valeur moyenne d'un fichier GeoTIFF uploadé.
     *
     * @param file  Le fichier GeoTIFF uploadé via le formulaire
     * @return      La moyenne des valeurs valides du raster
     * @throws IOException si le fichier ne peut pas être lu
     */
    public double extraireValeurMoyenne(MultipartFile file) throws IOException {

        // 1. Sauvegarde temporaire du fichier uploadé sur le disque
        //    (GeoTools nécessite un File, pas un InputStream)
        Path tempDir  = Files.createTempDirectory("raster_");
        File tempFile = tempDir.resolve(
                file.getOriginalFilename() != null
                        ? file.getOriginalFilename()
                        : "raster.tif"
        ).toFile();
        file.transferTo(tempFile);

        try {
            return calculerMoyenne(tempFile);
        } finally {
            // Nettoyage du fichier temporaire
            tempFile.delete();
            tempDir.toFile().delete();
        }
    }

    /**
     * Lit le GeoTIFF et calcule la moyenne des pixels valides (non-NoData).
     */
    private double calculerMoyenne(File tifFile) throws IOException {
        // 2. Détection automatique du format (GeoTIFF, etc.)
        AbstractGridFormat format = GridFormatFinder.findFormat(tifFile);

        Hints hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
        GridCoverage2DReader reader = format.getReader(tifFile, hints);

        if (reader == null) {
            throw new IOException("Impossible de lire le fichier GeoTIFF : " + tifFile.getName());
        }

        // 3. Lecture du raster
        GridCoverage2D coverage = reader.read(null);
        Raster rasterData = coverage.getRenderedImage().getData();

        int width    = rasterData.getWidth();
        int height   = rasterData.getHeight();
        int numBands = rasterData.getNumBands();  // généralement 1 pour un raster thématique

        double sum   = 0.0;
        long   count = 0L;

        // 4. Parcours de tous les pixels
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // On lit le premier band (index 0)
                double pixelValue = rasterData.getSampleDouble(
                        rasterData.getMinX() + x,
                        rasterData.getMinY() + y,
                        0  // band index
                );

                // Ignore les valeurs NoData et NaN
                if (pixelValue != NO_DATA_VALUE
                        && !Double.isNaN(pixelValue)
                        && !Double.isInfinite(pixelValue)) {
                    sum += pixelValue;
                    count++;
                }
            }
        }

        reader.dispose();
        coverage.dispose(true);

        if (count == 0) {
            throw new IOException("Le raster ne contient aucune valeur valide (tous les pixels sont NoData).");
        }

        // 5. Retourne la moyenne arrondie à 4 décimales
        return Math.round((sum / count) * 10000.0) / 10000.0;
    }

    /**
     * Extrait des statistiques complètes (min, max, moyenne, écart-type).
     * Utile pour les facteurs complexes.
     */
    public RasterStats extraireStatistiques(MultipartFile file) throws IOException {
        Path tempDir  = Files.createTempDirectory("raster_");
        File tempFile = tempDir.resolve(
                file.getOriginalFilename() != null ? file.getOriginalFilename() : "raster.tif"
        ).toFile();
        file.transferTo(tempFile);

        try {
            AbstractGridFormat format = GridFormatFinder.findFormat(tempFile);
            GridCoverage2DReader reader = format.getReader(tempFile);
            GridCoverage2D coverage = reader.read(null);
            Raster rasterData = coverage.getRenderedImage().getData();

            int width  = rasterData.getWidth();
            int height = rasterData.getHeight();

            double sum = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            long count = 0;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    double v = rasterData.getSampleDouble(
                            rasterData.getMinX() + x,
                            rasterData.getMinY() + y, 0
                    );
                    if (v != NO_DATA_VALUE && !Double.isNaN(v) && !Double.isInfinite(v)) {
                        sum += v;
                        count++;
                        if (v < min) min = v;
                        if (v > max) max = v;
                    }
                }
            }

            reader.dispose();
            coverage.dispose(true);

            if (count == 0) throw new IOException("Raster vide.");

            double moyenne = sum / count;

            // Calcul écart-type (2ème passe)
            double sumSq = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    double v = rasterData.getSampleDouble(
                            rasterData.getMinX() + x,
                            rasterData.getMinY() + y, 0
                    );
                    if (v != NO_DATA_VALUE && !Double.isNaN(v)) {
                        sumSq += Math.pow(v - moyenne, 2);
                    }
                }
            }
            double ecartType = Math.sqrt(sumSq / count);

            return new RasterStats(
                    Math.round(moyenne  * 10000.0) / 10000.0,
                    Math.round(min      * 10000.0) / 10000.0,
                    Math.round(max      * 10000.0) / 10000.0,
                    Math.round(ecartType* 10000.0) / 10000.0,
                    count
            );

        } finally {
            tempFile.delete();
            tempDir.toFile().delete();
        }
    }

    /** Classe interne pour les statistiques complètes */
    public record RasterStats(
            double moyenne,
            double min,
            double max,
            double ecartType,
            long   nombrePixels
    ) {}
}