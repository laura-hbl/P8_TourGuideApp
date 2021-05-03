package tripDeals.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tripDeals.dto.ProviderDTO;
import tripDeals.util.DTOConverter;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Contains methods that deals with TripDeals business logic.
 *
 * @author Laura Habdul
 */
@Service
public class TripDealsService implements ITripDealsService {

    /**
     * TripDealsService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TripDealsService.class);

    /**
     * TripPricer instance.
     */
    private final TripPricer tripPricer;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * Constructor of class UserService.
     * Initialize userRepository, dtoConverter and passwordEncoder.
     *
     * @param tripPricer   TripPricer instance
     * @param dtoConverter DTOConverter instance
     */
    @Autowired
    public TripDealsService(final TripPricer tripPricer, final DTOConverter dtoConverter) {
        this.tripPricer = tripPricer;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Calls TripPricer's getPrice method to obtain trip deals by providers based on user's reward points
     * and preferences. Each provider is converted to a dto object by calling DTOConverter's toProviderDTO
     * method then collect them to a List.
     *
     * @param apiKey       Api key
     * @param userId       Id of the user
     * @param adults       User's travel preference for number of adults
     * @param children     User's travel preference for number of children
     * @param nightsStay   User's travel preference for length of stay
     * @param rewardPoints Reward points redeemable for trip discounts
     * @return The provider list
     */
    public List<ProviderDTO> getProviders(final String apiKey, final UUID userId, int adults, int children,
                                          int nightsStay, int rewardPoints) {
        LOGGER.debug("Inside TripDealsService.getProviders for userId : " + userId.toString());

        List<ProviderDTO> providerList = new ArrayList<>();
        List<Provider> providers = tripPricer.getPrice(apiKey, userId, adults, children, nightsStay, rewardPoints);

        providers.forEach(provider -> {
            providerList.add(dtoConverter.toProviderDTO(provider));
        });

        return providerList;
    }
}
