package tourGuide.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.tracker.Tracker;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Permits to generates internal users for testing purposes (default value = 100).
 *
 * @author Laura Habdul
 */
@Component
public class InternalTestHelper {

    /**
     * InternalTestHelper logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(Tracker.class);

    /**
     * Creates a HashMap instance that map internal user to their username.
     */
    private final Map<String, User> internalUserMap = new HashMap<>();

    /**
     * The API Key that is mandatory to use TripPricer.
     */
    @Value("${trip.pricer.api.key}")
    private String TRIP_PRICER_API_KEY;

    /**
     * Numbers of internal users generated.
     */
    @Value("${test.user.numbers}")
    private int internalUserNumber;

    /**********************************************************************************
     *
     * Methods Below: For Internal Testing
     *
     **********************************************************************************/
    // Database connection will be used for external users, but for testing purposes internal users are
    // provided and stored in memory


    /**
     * Initializes internal users and add them to a map.
     */
    public void initializeInternalUsers() {
        IntStream.range(0, internalUserNumber).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName, phone, email);
            generateUserLocationHistory(user);

            internalUserMap.put(userName, user);
        });
        LOGGER.debug("Created " + internalUserNumber + " internal test users.");
    }

    /**
     * Generates an history of 3 visited location for the given user.
     *
     * @param user The internal user
     */
    private void generateUserLocationHistory(User user) {
        IntStream.range(0, 3).forEach(i -> {
            user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(),
                    generateRandomLongitude()), getRandomTime()));
        });
    }

    /**
     * Generates random location's longitude.
     *
     * @return the random location's longitude generated
     */
    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Generates random location's latitude.
     *
     * @return the random location's latitude generated
     */
    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    /**
     * Generates random LocalDateTime.
     *
     * @return The random LocalDateTime generated
     */
    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

    /**
     * TRIP_PRICER_API_KEY getter.
     *
     * @return The API Key that is mandatory to use TripPricer
     */
    public String getTripPricerApiKey() {
        return TRIP_PRICER_API_KEY;
    }

    /**
     * InternalUserMap getter.
     *
     * @return The HashMap of internal users mapped to their username
     */
    public Map<String, User> getInternalUserMap() {
        return internalUserMap;
    }
}
