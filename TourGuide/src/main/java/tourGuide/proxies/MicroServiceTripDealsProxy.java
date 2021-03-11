package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.dto.ProviderListDTO;

import java.util.UUID;

@FeignClient(value = "tripdeals-microservice", url = "${proxy.tripdeals}")
public interface MicroServiceTripDealsProxy {

    @GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardsPoints}")
    ProviderListDTO getProviders(@PathVariable final String apiKey, @PathVariable final UUID userId,
                                 @PathVariable final int adults, @PathVariable final int children,
                                 @PathVariable final int nightsStay, @PathVariable final int rewardsPoints);
}
