package tourGuide.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import tourGuide.model.Location;

/**
 * Calculates the distance between two locations.
 *
 * @author Laura Habdul
 */
@Component
@NoArgsConstructor
public class DistanceCalculator {

    public static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

    /**
     * Calculates the distance between two locations in statute miles.
     *
     * @param loc1 location
     * @param loc2 location
     * @return The distance in statute miles
     */
    public double getDistanceInMiles(final Location loc1, final Location loc2) {

        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }
}
