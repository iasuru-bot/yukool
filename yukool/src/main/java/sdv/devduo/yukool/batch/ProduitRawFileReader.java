package sdv.devduo.yukool.batch;

import lombok.NoArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sdv.devduo.yukool.dto.ProduitRaw;
import sdv.devduo.yukool.shared.SharedProductRawHolder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ProduitRawFileReader implements ItemReader<ProduitRaw>, InitializingBean {

    private SharedProductRawHolder sharedProductRawHolder;
    private List<String> lines;
    private Iterator<String> iterator;

    @Autowired
    public ProduitRawFileReader(SharedProductRawHolder sharedProductRawHolder) {
        this.sharedProductRawHolder = sharedProductRawHolder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Path path = Paths.get("src/main/resources/data/open-food-facts.csv");
        lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.removeFirst(); // remove header
        iterator = lines.iterator();
    }

    @Override
    public ProduitRaw read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.hasNext()) {
            String line = iterator.next();
            return parseLineToProductRaw(line);
        }
        return null;
    }

    private ProduitRaw parseLineToProductRaw(String line) {
        String[] parts = line.split("\\|", -1);

        if (parts.length < 30) {
            System.err.println("Ligne ignorée (incomplète) : " + line);
            return null;
        }

        return new ProduitRaw(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10], parts[11], parts[12], parts[13], parts[14], parts[15], parts[16], parts[17], parts[18], parts[19], parts[20], parts[21], parts[22], parts[23], parts[24], parts[25], parts[26], parts[27], parts[28], parts[29]);

    }

    public void readAllToHolder() throws Exception {
        Path path = Paths.get("src/main/resources/data/open-food-facts.csv");
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.removeFirst();
        List<ProduitRaw> produits = new ArrayList<>();
        for (String line : lines) {
            ProduitRaw pr = parseLineToProductRaw(line);
            System.out.println(pr);
            if (pr != null) {
                produits.add(pr);
            }
        }

       sharedProductRawHolder.setProduitRawList(produits);
    }
}
