package gps.config;

import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

/**
 * Gps application configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
public class GpsBeans {

    /**
     * Creates a Local bean that permits to set the local default value to Local.US.
     *
     * @return the value of the default locale for this instance
     */
    @Bean
    public Locale getLocale() {
        Locale.setDefault(Locale.US);
        return Locale.getDefault();
    }

    /**
     * Creates a GpsUtil bean.
     *
     * @return a GpsUtil object
     */
    @Bean
    public GpsUtil getGpsUtil() {
        return new GpsUtil();
    }
}
