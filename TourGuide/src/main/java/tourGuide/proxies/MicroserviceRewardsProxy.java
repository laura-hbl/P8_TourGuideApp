package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "rewards-microservice", url = "${proxy.rewards}")
public interface MicroserviceRewardsProxy {

    @GetMapping("/points/{attractionId}/{userId}")
    int getRewardPoints(@PathVariable final UUID attractionId, @PathVariable final UUID userId);
}
