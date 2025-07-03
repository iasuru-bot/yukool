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
import sdv.devduo.yukool.service.CsvImportService;

@SpringBootApplication
public class YukoolApplication  {

	public static void main(String[] args) {
		SpringApplication.run(YukoolApplication.class, args);
	}

	/**
	 *
	 * @param csvImportService
	 * @return
	 */
	@Bean
	public CommandLineRunner runCsvImport(@Autowired CsvImportService csvImportService) {
		return args -> {
			String csvPath = "src/main/resources/data/open-food-facts.csv";
			csvImportService.importCsv(csvPath);
		};
	}
}
