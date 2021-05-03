package rewards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

/**
 * Contains methods that deals with rewards business logic.
 *
 * @author Laura Habdul
 */
@Service
public class RewardsService implements IRewardsService {

    /**
     * RewardCentral instance.
     */
    private final RewardCentral rewardCentral;

    /**
     * Constructor of class RewardsService.
     * Initialize rewardsCentral.
     *
     * @param rewardCentral RewardCentral instance
     */
    @Autowired
    public RewardsService(final RewardCentral rewardCentral) {
        this.rewardCentral = rewardCentral;
    }

    /**
     * Calls RewardCentral's getAttractionRewardPoints method to retrieves reward points acquired by the user
     * after visiting the tourist attraction.
     *
     * @param attractionId Id of the attraction
     * @param userId       Id of the user
     * @return Reward points
     */
    public int getAttractionRewardPoints(final UUID attractionId, final UUID userId) {

        int rewardPoints = rewardCentral.getAttractionRewardPoints(attractionId, userId);

        return rewardPoints;
    }
}
