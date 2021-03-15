package tourGuide.unit.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tourGuide.model.Location;
import tourGuide.util.DistanceCalculator;

import static org.assertj.core.api.Assertions.assertThat;

public class DistanceCalculatorTest {

    private DistanceCalculator distanceCalculator = new DistanceCalculator();

    @Test
    @DisplayName("Given locations, when getDistanceInMiles, then result should match expected distance")
    public void givenLocations_whenGetDistanceInMiles_thenReturnExpectedDistance() {

        Location userLocation = new Location(-160.326003, -73.869629);
        Location attractionLocation = new Location(-160.326003, -73.869629);
        double expectedDistance = 0;

        double result = distanceCalculator.getDistanceInMiles(userLocation, attractionLocation);

        assertThat(result).isEqualTo(expectedDistance);
    }
}
