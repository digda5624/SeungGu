package digda.lab.lab.batchinsert;

import digda.lab.lab.lock.LockApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication(scanBasePackages = "digda.lab.lab.batchinsert")
public class BatchInsertApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchInsertApplication.class, args);
    }
}
