package tourGuide.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;

@Data
@AllArgsConstructor
public class UserReward {

	public final VisitedLocation visitedLocation;

	public final Attraction attraction;

	public int rewardPoints;
}
