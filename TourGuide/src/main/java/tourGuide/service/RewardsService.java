package tourGuide.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.constant.ProximityBuffer;
import tourGuide.dto.AttractionDTO;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;
import tourGuide.proxies.MicroserviceGpsProxy;
import tourGuide.proxies.MicroserviceRewardsProxy;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.ModelConverter;

import java.util.List;
import java.util.concurrent.*;

/**
 * Contains methods that deals with rewards business logic.
 *
 * @author Laura Habdul
 */
@Service
public class RewardsService implements IRewardsService {

    /**
     * RewardsService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(RewardsService.class);

    /**
     * Creates an Executor with fixed thread pool.
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(1000);

    /**
     * MicroserviceGpsProxy instance.
     */
    private final MicroserviceGpsProxy gpsProxy;

    /**
     * MicroserviceRewardsProxy instance.
     */
    private final MicroserviceRewardsProxy rewardsProxy;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * DistanceCalculator instance.
     */
    private final DistanceCalculator distanceCalculator;


    /**
     * Constructor of class RewardsService.
     * Initializes gpsProxy, rewardsProxy, modelConverter and distanceCalculator.
     *
     * @param gpsProxy           MicroserviceGpsProxy instance
     * @param rewardsProxy       MicroserviceRewardsProxy instance
     * @param modelConverter     ModelConverter instance
     * @param distanceCalculator DistanceCalculator instance
     */
    @Autowired
    public RewardsService(final MicroserviceGpsProxy gpsProxy, final MicroserviceRewardsProxy rewardsProxy,
                          final ModelConverter modelConverter, final DistanceCalculator distanceCalculator) {
        this.gpsProxy = gpsProxy;
        this.rewardsProxy = rewardsProxy;
        this.modelConverter = modelConverter;
        this.distanceCalculator = distanceCalculator;
    }

    /**
     * Call the getAttractions method of GpsProxy to retrieve the list of attractions and loop through
     * the user's history of visited locations to check which attraction the user has visited by calling the
     * isNearAttraction method. Then, for each attraction visited, checks if the user has already received a reward,
     * otherwise calculates the reward points acquired after visiting the attraction by calling the getRewardPoints
     * method of RewardsProxy and then adds a new UserReward object in the list of user rewards .
     *
     * @param user The user whose rewards are being calculated
     */
    public void calculateRewards(final User user) {

        CopyOnWriteArrayList<VisitedLocation> visitedLocation = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<AttractionDTO> attractions = new CopyOnWriteArrayList<>();

        visitedLocation.addAll(user.getVisitedLocations());
        attractions.addAll(gpsProxy.getAttractions());

        visitedLocation.forEach(v -> {
            attractions.stream()
                    .filter(a -> isNearAttraction(v, a.getLocation()))
                    .forEach(a -> {
                        if (user.getUserRewards().stream()
                                .noneMatch(r -> r.attraction.getAttractionName().equals(a.getAttractionName()))) {
                            int rewardsPoint = rewardsProxy.getRewardPoints(a.getAttractionId(), user.getUserId());
                            user.addUserReward(new UserReward(v, modelConverter.toAttraction(a), rewardsPoint));
                        }
                    });
        });
    }

    /**
     * Calculates all user rewards asynchronously.
     *
     * @param users The user list
     */
    public void calculateAllRewards(final List<User> users) {
        LOGGER.debug("Inside RewardsService.calculateAllRewards");

        for (User user : users) {
            Runnable runnable = () -> {
                calculateRewards(user);
            };
            executorService.execute(runnable);
        }
        shutdown();
    }

    /**
     * Calls DistanceCalculator's getDistanceInMiles method to calculates the distance in miles between the user location
     * and the attraction location. If distance is less than DEFAULT_PROXIMITY_BUFFER return true, else return false.
     *
     * @param visitedLocation    The user visited location
     * @param attractionLocation The attraction location
     * @return True if distance is less than DEFAULT_PROXIMITY_BUFFER, false is distance is higher.
     */
    private boolean isNearAttraction(final VisitedLocation visitedLocation, final Location attractionLocation) {

        return !(distanceCalculator.getDistanceInMiles(attractionLocation, visitedLocation.getLocation()) >
                ProximityBuffer.DEFAULT_PROXIMITY_BUFFER);
    }

    /**
     * Shuts down executor service after timed out.
     */
    public void shutdown() {
        LOGGER.debug("Inside RewardsService.shutdown");

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(20, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
