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
     * Calculates all user rewards asynchronously.
     *
     * @param users The user list
     */
    void calculateAllRewards(List<User> users);

    /**
     * Calculates the user rewards.
     *
     * @param user The user
     */
    void calculateRewards(final User user);

    /**
     * Shuts down the executor service.
     */
    void shutdown() throws InterruptedException;
}
