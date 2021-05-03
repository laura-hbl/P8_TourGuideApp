package tripDeals.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripDeals.dto.ProviderDTO;
import tripDeals.exception.ResourceNotFoundException;
import tripDeals.service.ITripDealsService;

import java.util.List;
import java.util.UUID;

/**
 * Creates REST endpoints for operations on trip deals data.
 *
 * @author Laura Habdul
 * @see ITripDealsService
 */
@RestController
@RequestMapping("/tripDeals")
public class TripDealsController {

    /**
     * TripDealsController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TripDealsController.class);

    /**
     * ITripDealsService's implement class reference.
     */
    private ITripDealsService tripDealsService;

    /**
     * Constructor of class TripDealsController.
     * Initialize tripDealsService.
     *
     * @param tripDealsService ITripDealsService's implement class reference.
     */
    @Autowired
    public TripDealsController(final ITripDealsService tripDealsService) {
        this.tripDealsService = tripDealsService;
    }

    /**
     * Retrieves trip deals by providers based on user's reward points and preferences. If the provider list is empty
     * ResourceNotFoundException is thrown.
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
    public List<ProviderDTO> getProviders(@PathVariable final String apiKey, @PathVariable final UUID userId,
                                          @PathVariable final int adults, @PathVariable final int children,
                                          @PathVariable final int nightsStay, @PathVariable final int rewardPoints) {
        LOGGER.debug("Provider list request with user id: {}", userId.toString());

        List<ProviderDTO> providers = tripDealsService.getProviders(apiKey, userId, adults, children,
                nightsStay, rewardPoints);

        if (providers.isEmpty()) {
            throw new ResourceNotFoundException("Failed to get provider list");
        }

        LOGGER.info("Provider list request - SUCCESS");
        return providers;
    }
}
