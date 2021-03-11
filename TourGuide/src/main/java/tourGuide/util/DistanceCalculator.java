package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.constant.UnitsConversion;
import tourGuide.model.Location;

import java.util.concurrent.TimeUnit;

@Component
public class DistanceCalculator {

    public DistanceCalculator() {
    }

    public double getDistanceInMiles(final Location loc1, final Location loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = UnitsConversion.STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }
}
