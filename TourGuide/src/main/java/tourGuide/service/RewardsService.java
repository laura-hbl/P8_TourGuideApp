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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final ExecutorService executorService = Executors.newFixedThreadPool(900);

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
        LOGGER.debug("Inside RewardsService.calculateRewards");

        CopyOnWriteArrayList<VisitedLocation> userLocations = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<AttractionDTO> attractions = new CopyOnWriteArrayList<>();

        userLocations.addAll(user.getVisitedLocations());
        attractions.addAll(gpsProxy.getAttractions());

        userLocations.forEach(visitedLocation -> {
            attractions.stream()
                    .filter(attraction -> isNearAttraction(visitedLocation, attraction.getLocation()))
                    .forEach(attraction -> {
                        if (user.getUserRewards().stream()
                                .noneMatch(r -> r.attraction.getAttractionName().equals(attraction.getAttractionName()))) {
                            user.addUserReward(new UserReward(visitedLocation, modelConverter.toAttraction(attraction),
                                    rewardsProxy.getRewardPoints(attraction.getAttractionId(), user.getUserId())));
                        }
                    });
        });
    }

    /**
     * Calculates user reward asynchronously.
     * Method only used to calculate the rewards of all users.
     *
     * @param user The user
     */
    public CompletableFuture<?> calculateRewardAsync(final User user) {
        LOGGER.debug("Inside RewardsService.calculateRewardAsync");

        return CompletableFuture.runAsync(() -> {
            this.calculateRewards(user);
        }, executorService);
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
        LOGGER.debug("Inside RewardsService.isNearAttraction");

        return !(distanceCalculator.getDistanceInMiles(attractionLocation, visitedLocation.getLocation()) >
                ProximityBuffer.DEFAULT_PROXIMITY_BUFFER);
    }
}
