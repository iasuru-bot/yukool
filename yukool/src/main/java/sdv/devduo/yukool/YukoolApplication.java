package sdv.devduo.yukool;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import sdv.devduo.yukool.dto.ProduitRaw;
import sdv.devduo.yukool.service.ProduitService;
import sdv.devduo.yukool.utils.CsvDataParser;
import sdv.devduo.yukool.utils.CsvDataReader;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class YukoolApplication implements CommandLineRunner  {

	@Autowired
	private ProduitService produitService;

	public static void main(String[] args) {
		SpringApplication.run(YukoolApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		long startTime = System.currentTimeMillis();
		String csvPath = "data/open-food-facts.csv";
		List<ProduitRaw> produitRawsList = CsvDataReader.readCsv(csvPath);

		long endParseTime = System.currentTimeMillis();
		System.out.println("Temps d'execution du parse : " + (endParseTime - startTime) + " ms");

		//transforme
		produitService.saveAllProduit(produitRawsList);

		long endTime = System.currentTimeMillis();
		System.out.println("Temps d'execution : " + (endTime - startTime) + " ms");

	}
}
