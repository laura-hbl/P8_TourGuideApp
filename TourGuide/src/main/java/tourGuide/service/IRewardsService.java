package tourGuide.service;

import tourGuide.model.user.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * RewardsService interface.
 *
 * @author Laura Habdul
 */
public interface IRewardsService {

    /**
     * Calculates the user rewards.
     *
     * @param user The user
     */
    void calculateRewards(final User user);
}
