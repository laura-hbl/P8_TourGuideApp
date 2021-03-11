package tourGuide.dto;

import tourGuide.model.Attraction;
import tourGuide.model.VisitedLocation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRewardDTO {

    private VisitedLocation visitedLocation;

    private Attraction attraction;

    private int rewardPoints;
}
