package boho.lottonumbergenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LottonumbergeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LottonumbergeneratorApplication.class, args);
	}
}
