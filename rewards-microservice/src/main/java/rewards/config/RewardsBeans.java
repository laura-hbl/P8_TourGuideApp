package rewards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

import java.util.Locale;

/**
 * Gps application configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
public class RewardsBeans {

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
     * Creates a RewardCentral bean.
     *
     * @return a RewardCentral object
     */
    @Bean
    public RewardCentral getRewardCentral() {
        return new RewardCentral();
    }
}
