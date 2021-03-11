package tourGuide.service;

import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;

public interface IRewardsService {

    void calculateRewards(final User user);

    void calculateRewardsWithThreads(final User user);

    void shutdown() throws InterruptedException;
}
