package digda.lab.lab.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "digda.lab.lab.lock")
public class LockApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockApplication.class, args);
	}

}
