package tripDeals.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tripPricer.TripPricer;

import java.util.Locale;

/**
 * TripDeals application configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
public class TripDealsBeans {

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
     * Creates a TripPricer bean.
     *
     * @return a TripPricer object
     */
    @Bean
    public TripPricer getTripPricer() {
        return new TripPricer();
    }
}
