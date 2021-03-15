package tourGuide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Locale;

@SpringBootApplication
@EnableFeignClients("tourGuide")
public class Application {

    public static void main(final String[] args) {
        Locale.setDefault(Locale.US);
        SpringApplication.run(Application.class, args);
    }
}
