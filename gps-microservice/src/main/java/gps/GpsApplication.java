package gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class GpsApplication {

    public static void main(final String[] args) {
        Locale.setDefault(Locale.US);
        SpringApplication.run(GpsApplication.class, args);
    }
}
