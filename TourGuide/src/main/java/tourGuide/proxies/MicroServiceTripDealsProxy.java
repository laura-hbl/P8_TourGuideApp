package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.dto.ProviderDTO;

import java.util.List;
import java.util.UUID;

/**
 * Permits to connection between TourGuide application and tripdeals-microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "tripdeals-microservice", url = "${proxy.tripdeals}")
public interface MicroServiceTripDealsProxy {

    /**
     * Retrieves trip deals by providers based on user's reward points and preferences.
     *
     * @param apiKey       Api key
     * @param userId       Id of the user
     * @param adults       User's travel preference for number of adults
     * @param children     User's travel preference for number of children
     * @param nightsStay   User's travel preference for length of stay
     * @param rewardPoints Reward points redeemable for trip discounts
     * @return The provider list
     */
    @GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardPoints}")
    List<ProviderDTO> getProviders(@PathVariable final String apiKey, @PathVariable final UUID userId,
                                   @PathVariable final int adults, @PathVariable final int children,
                                   @PathVariable final int nightsStay, @PathVariable final int rewardPoints);
}
