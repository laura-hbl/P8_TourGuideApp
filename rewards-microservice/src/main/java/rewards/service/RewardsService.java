package rewards.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
public class RewardsService implements IRewardsService {

	private final RewardCentral rewardsCentral;

	@Autowired
	public RewardsService(final RewardCentral rewardCentral) {
		this.rewardsCentral = rewardCentral;
	}

	public int getAttractionRewardPoints(final UUID attractionId, final UUID userId) {

		return rewardsCentral.getAttractionRewardPoints(attractionId, userId);
	}
}
