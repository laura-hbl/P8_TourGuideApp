package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.model.Location;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.UserReward;

/**
 * Allows the conversion of Model class to DTO class.
 *
 * @author Laura Habdul
 */
@Component
public class DTOConverter {

    /**
     * Converts UserPreferences to UserPreferencesDTO.
     *
     * @param preferences User trip deals preferences
     * @return The UserPreferencesDTO object
     */
    public UserPreferencesDTO toUserPreferencesDTO(final UserPreferences preferences) {

        return new UserPreferencesDTO(preferences.getAttractionProximity(), preferences.getLowerPricePoint()
                .getNumber().intValue(), preferences.getHighPricePoint().getNumber().intValue(),
                preferences.getTripDuration(), preferences.getTicketQuantity(), preferences.getNumberOfAdults(),
                preferences.getNumberOfChildren());
    }

    /**
     * Converts UserReward to UserRewardDTO.
     *
     * @param userReward UserReward object to convert
     * @return The UserRewardDTO object
     */
    public UserRewardDTO toUserRewardDTO(final UserReward userReward) {

        return new UserRewardDTO(userReward.visitedLocation, userReward.attraction, userReward.getRewardPoints());
    }

    /**
     * Converts Location to LocationDTO.
     *
     * @param location Location object to convert
     * @return The LocationDTO object
     */
    public LocationDTO toLocationDTO(final Location location) {

        return new LocationDTO(location.getLatitude(), location.getLongitude());
    }
}
