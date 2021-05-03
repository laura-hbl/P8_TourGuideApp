package tourGuide.dto;

import lombok.Getter;
import lombok.Setter;
import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Permits the storage and retrieving data of an user reward.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
public class UserRewardDTO {

    /**
     * The visitedLocation that gives this reward.
     */
    private VisitedLocation visitedLocation;

    /**
     * The attraction concerned by this reward.
     */
    private Attraction attraction;

    /**
     * Reward points acquired after visiting the attraction.
     */
    private int rewardPoints;
}
