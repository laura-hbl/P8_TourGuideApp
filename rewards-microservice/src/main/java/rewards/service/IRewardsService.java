package rewards.service;

import java.util.UUID;

public interface IRewardsService {

    int getAttractionRewardPoints(final UUID attractionId, final UUID userId);
}
