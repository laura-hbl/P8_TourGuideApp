package tripDeals.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tripPricer.TripPricer;

@Configuration
public class TripDealsBeans {

    @Bean
    public TripPricer getTripPricer() {
        return new TripPricer();
    }
}
