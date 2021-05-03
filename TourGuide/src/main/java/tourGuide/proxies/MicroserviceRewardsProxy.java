package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * Permits to connection between TourGuide application and rewards-microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "rewards-microservice", url = "${proxy.rewards}")
public interface MicroserviceRewardsProxy {

    /**
     * Retrieves reward points acquired after visiting the tourist attraction.
     *
     * @param attractionId Id of the attraction
     * @param userId       Id of the user
     * @return Reward points
     */
    @GetMapping("/points/{attractionId}/{userId}")
    int getRewardPoints(@PathVariable final UUID attractionId, @PathVariable final UUID userId);
}
