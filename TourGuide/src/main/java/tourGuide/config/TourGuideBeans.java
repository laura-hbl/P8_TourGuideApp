package tourGuide.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tourGuide.exception.FeignErrorDecoder;

import java.util.Locale;

/**
 * TourGuide application configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
public class TourGuideBeans {

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
     * Creates a FeignErrorDecoder bean.
     *
     * @return the FeignErrorDecoder instance
     */
    @Bean
    public FeignErrorDecoder myErrorDecoder() {
        return new FeignErrorDecoder();
    }

    /**
     * Creates a Retryer bean.
     *
     * @return The default Retryer instance
     */
    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }
}
