package ibf2024.assessment.paf.batch4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import ibf2024.assessment.paf.batch4.repositories.BeerRepository;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Batch2Application{

	public static void main(String[] args) {
		SpringApplication.run(Batch2Application.class, args);
	}

}
