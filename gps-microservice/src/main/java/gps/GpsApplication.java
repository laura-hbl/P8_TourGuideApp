package gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

/**
 * Launch Gps Application.
 *
 * @author Laura Habdul
 */
@SpringBootApplication
public class GpsApplication {

    /**
     * Starts Gps application.
     *
     * @param args no argument
     */
    public static void main(final String[] args) {
        SpringApplication.run(GpsApplication.class, args);
    }
}
