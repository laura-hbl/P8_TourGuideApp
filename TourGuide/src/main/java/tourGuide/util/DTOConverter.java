package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.model.Location;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.UserReward;

@Component
public class DTOConverter {

    public UserPreferencesDTO toUserPreferencesDTO(final UserPreferences preferences) {

        return new UserPreferencesDTO(preferences.getAttractionProximity(), preferences.getLowerPricePoint()
                .getNumber().intValue(), preferences.getHighPricePoint().getNumber().intValue(),
                preferences.getTripDuration(), preferences.getTicketQuantity(), preferences.getNumberOfAdults(),
                preferences.getNumberOfChildren());
    }

    public UserRewardDTO toUserRewardDTO(UserReward userReward) {

        return new UserRewardDTO(userReward.visitedLocation, userReward.attraction, userReward.getRewardPoints());
    }

    public LocationDTO toLocationDTO(Location location) {

        return new LocationDTO(location.getLatitude(), location.getLongitude());
    }
}
