package rewards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

@Configuration
public class RewardsBeans {

    @Bean
    public RewardCentral getRewardCentral() {
        return new RewardCentral();
    }
}
