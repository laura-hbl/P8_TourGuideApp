package tourGuide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Launch TourGuide Application.
 *
 * @author Laura Habdul
 */
@SpringBootApplication
@EnableFeignClients("tourGuide")
public class Application {

    /**
     * Starts TourGuide application.
     *
     * @param args no argument
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
