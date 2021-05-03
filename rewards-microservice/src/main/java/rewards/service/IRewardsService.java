package rewards.service;

import java.util.UUID;

/**
 * RewardsService interface.
 *
 * @author Laura Habdul
 */
public interface IRewardsService {

    /**
     * Retrieves reward points acquired by the user after visiting the tourist attraction.
     *
     * @param attractionId Id of the attraction
     * @param userId       Id of the user
     * @return Reward points
     */
    int getAttractionRewardPoints(final UUID attractionId, final UUID userId);
}
