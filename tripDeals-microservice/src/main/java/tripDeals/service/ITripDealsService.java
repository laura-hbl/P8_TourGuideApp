package tripDeals.service;

import tripDeals.dto.ProviderDTO;

import java.util.List;
import java.util.UUID;

/**
 * TripDealsService interface.
 *
 * @author Laura Habdul
 */
public interface ITripDealsService {

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
    List<ProviderDTO> getProviders(final String apiKey, final UUID userId, final int adults, final int children,
                                   final int nightsStay, final int rewardPoints);
}
