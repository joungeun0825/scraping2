package scraping.bbq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BbqApplication {

	public static void main(String[] args) throws InterruptedException, IOException {
		SpringApplication.run(BbqApplication.class, args);
	}

}
