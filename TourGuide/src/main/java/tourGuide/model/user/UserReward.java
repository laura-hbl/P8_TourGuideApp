package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;

/**
 * Permits the storage and retrieving data of an user reward.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
public class UserReward {

	/**
	 * The visitedLocation that gives this reward.
	 */
	public final VisitedLocation visitedLocation;

	/**
	 * The attraction concerned by this reward.
	 */
	public final Attraction attraction;

	/**
	 * Reward points acquired after visiting the attraction.
	 */
	public int rewardPoints;
}
