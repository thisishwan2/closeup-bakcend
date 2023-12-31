package farmSystem.closeUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloseUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloseUpApplication.class, args);
	}

}
