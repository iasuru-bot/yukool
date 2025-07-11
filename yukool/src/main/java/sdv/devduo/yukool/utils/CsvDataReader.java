package sdv.devduo.yukool.utils;

import lombok.extern.slf4j.Slf4j;
import sdv.devduo.yukool.dto.ProduitRaw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Class qui permet de lire le CSV et de ressortir une liste de {@link ProduitRaw}
 */
@Slf4j
public final class CsvDataReader {

    /**
     * Lis le CSV, Ignore la première colonne et met toutes les lignes dans une ArrayList
     * @param csvPath Chemin du CSV a lire
     * @return Arraylist du CSV traité
     * @throws Exception
     */
    public static List<ProduitRaw> readCsv(String csvPath) throws Exception {
        Path path= Paths.get(CsvDataParser.class.getClassLoader().getResource(csvPath).toURI());
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor(); Stream<String> lines = Files.lines(path)){

            Stream<String> linesWithoutHeader = lines.skip(1);
            List<Future<ProduitRaw>> futures = new ArrayList<>();
            linesWithoutHeader.forEach(line -> {
                futures.add(executor.submit(() -> CsvDataParser.parseLine(line)));
            });


            List<ProduitRaw> produitRaws = new ArrayList<>();
            futures.forEach(f -> {
                try {
                    produitRaws.add(f.get());
                } catch (Exception e) {
                    log.error("erreur", e);
                }
            });
            return produitRaws;
        }
    }
}
