package universe.universe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UnIverseApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnIverseApplication.class, args);
    }

}
