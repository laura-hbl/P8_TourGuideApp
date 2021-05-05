package tourGuide.service;

import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tourGuide.dto.*;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.ResourceNotFoundException;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.user.User;
import tourGuide.model.user.UserPreferences;
import tourGuide.proxies.MicroServiceTripDealsProxy;
import tourGuide.proxies.MicroserviceGpsProxy;
import tourGuide.proxies.MicroserviceRewardsProxy;
import tourGuide.tracker.Tracker;
import tourGuide.util.DTOConverter;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.ModelConverter;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Contains methods that deals with between user business logic.
 *
 * @author Laura Habdul
 */
@Service
public class TourGuideService implements ITourGuideService {

    /**
     * TourGuideService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TourGuideService.class);

    /**
     * Creates an Executor with fixed thread pool.
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(800);

    /**
     * MicroserviceGpsProxy instance.
     */
    private final MicroserviceGpsProxy gpsProxy;

    /**
     * MicroserviceRewardsProxy instance.
     */
    private final MicroserviceRewardsProxy rewardsProxy;

    /**
     * MicroServiceTripDealsProxy instance.
     */
    private final MicroServiceTripDealsProxy tripDealsProxy;

    /**
     * IRewardsService instance.
     */
    private final IRewardsService rewardsService;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * DistanceCalculator instance.
     */
    private final DistanceCalculator distanceCalculator;

    /**
     * InternalTestHelper instance.
     */
    private final InternalTestHelper internalTestHelper;

    /**
     * Tracker instance.
     */
    public Tracker tracker;

    /**
     * Indicates if test mode is enabled or not.
     */
    @Value("${test.mode.enabled}")
    private boolean isTestMode;

    /**
     * Indicates if integration tests are running nor not.
     */
    @Value("${performance.test.enabled}")
    private boolean isPerformanceTest;

    /**
     * Constructor of class TourGuideService.
     * Initializes gpsProxy, rewardsProxy, tripDealsProxy, rewardsService, internalTestHelper, modelConverter,
     * dtoConverter and distanceCalculator.
     *
     * @param gpsProxy           MicroserviceGpsProxy instance
     * @param rewardsProxy       MicroserviceRewardsProxy instance
     * @param tripDealsProxy     MicroServiceTripDealsProxy instance
     * @param rewardsService     IRewardsService instance's implement class reference
     * @param internalTestHelper InternalTestHelper instance
     * @param modelConverter     ModelConverter instance
     * @param dtoConverter       DTOConverter instance
     * @param distanceCalculator DistanceCalculator instance
     */
    @Autowired
    public TourGuideService(final MicroserviceGpsProxy gpsProxy, final MicroserviceRewardsProxy rewardsProxy,
                            final MicroServiceTripDealsProxy tripDealsProxy, final IRewardsService rewardsService,
                            final InternalTestHelper internalTestHelper, final ModelConverter modelConverter,
                            final DTOConverter dtoConverter, final DistanceCalculator distanceCalculator) {
        this.gpsProxy = gpsProxy;
        this.rewardsProxy = rewardsProxy;
        this.tripDealsProxy = tripDealsProxy;
        this.rewardsService = rewardsService;
        this.internalTestHelper = internalTestHelper;
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
        this.distanceCalculator = distanceCalculator;
    }

    /**
     * Initializes internal users and starts the tracker (except for integration tests).
     */
    @PostConstruct
    public void initialization() {

        if (isTestMode) {
            LOGGER.info("TestMode enabled");
            LOGGER.debug("Initializing users");
            internalTestHelper.initializeInternalUsers();
            LOGGER.debug("Finished initializing users");
        }
        if (!isPerformanceTest) {
            this.tracker = new Tracker(this);
            tracker.startTracking();
        }
    }

    /**
     * Calls InternalTestHelper's getInternalUserMap() method to retrieves the internal users and checks if given
     * the user's username is already used. If not, user is added. If so DataAlreadyRegisteredException is thrown.
     *
     * @param user The user to be added
     */
    public void addUser(final User user) {
        LOGGER.debug("Inside TourGuideService.addUser for username : " + user.getUserName());

        Map<String, User> internalUserMap = internalTestHelper.getInternalUserMap();

        if (internalUserMap.containsKey(user.getUserName())) {
            throw new DataAlreadyRegisteredException("The username provided may be used already");
        }

        internalUserMap.put(user.getUserName(), user);
    }

    /**
     * Calls InternalTestHelper's getInternalUserMap() method to retrieves the user with the given username and
     * checks if the user exists. If so ResourceNotFoundException is thrown.
     *
     * @param userName Username of the user to be found
     * @return The user found
     */
    public User getUser(final String userName) {
        LOGGER.debug("Inside TourGuideService.getUser for username : " + userName);

        User user = internalTestHelper.getInternalUserMap().get(userName);

        if (user == null) {
            throw new ResourceNotFoundException("No user registered with this username");
        }

        return user;
    }

    /**
     * Calls InternalTestHelper's getInternalUserMap() method to retrieves the internal users, collects them into a
     * List then checks if the user list is not empty. If not, user is added. If so ResourceNotFoundException is thrown.
     *
     * @return The user list
     */
    public List<User> getAllUsers() {
        LOGGER.debug("Inside TourGuideService.getAllUsers");

        List<User> users = internalTestHelper.getInternalUserMap().values().stream().collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Failed to get user list");
        }

        return users;
    }

    /**
     * Calls TourGuideService's getUser method to retrieves the user with the given username, updates the user preferences
     * object and converts it to a dto object by calling DTOConverter's toUserPreferencesDTO method.
     *
     * @param userName Username of the user
     * @return UserPreferencesDTO object that contains the user preferences
     */
    public UserPreferencesDTO updateUserPreferences(final String userName, UserPreferencesDTO userPreferences) {
        LOGGER.debug("Inside TourGuideService.getUserPreferences for username : " + userName);

        User user = getUser(userName);

        UserPreferences preferences = user.getUserPreferences();
        preferences.setAttractionProximity(userPreferences.getAttractionProximity());
        preferences.setHighPricePoint(Money.of(userPreferences.getHighPricePoint(), preferences.getCurrency()));
        preferences.setLowerPricePoint(Money.of(userPreferences.getLowerPricePoint(), preferences.getCurrency()));
        preferences.setTripDuration(userPreferences.getTripDuration());
        preferences.setTicketQuantity(userPreferences.getTicketQuantity());
        preferences.setNumberOfAdults(userPreferences.getNumberOfAdults());
        preferences.setNumberOfChildren(userPreferences.getNumberOfChildren());

        UserPreferencesDTO userPreferencesUpdated = dtoConverter.toUserPreferencesDTO(preferences);

        return userPreferencesUpdated;
    }

    /**
     * Calls TourGuideService's getUser method to retrieves the user with the given username, retrieves user rewards
     * converts each of them to a dto object by calling DTOConverter's toUserRewardDTO method then adds them to a list.
     *
     * @param userName Username of the user
     * @return The list of the user rewards
     */
    public List<UserRewardDTO> getUserRewards(final String userName) {
        LOGGER.debug("Inside TourGuideService.getUserRewards for username : " + userName);

        User user = getUser(userName);

        List<UserRewardDTO> userRewards = new ArrayList<>();
        user.getUserRewards().stream()
                .forEach(reward -> {
                    userRewards.add(dtoConverter.toUserRewardDTO(reward));
                });

        return userRewards;
    }

    /**
     * Tracks user location by calling GpsProxy's getUserLocation method, converts the VisitedLocationDTO object to
     * a model object by calling ModelConverter's toVisitedLocation method and add it to the user visitedLocation
     * history. Then, calculates user rewards by calling RewardsService's calculateRewards method.
     *
     * @param user The user to be located
     * @return The tracked location of the user
     */
    public VisitedLocationDTO trackUserLocation(final User user) {
        LOGGER.debug("Inside TourGuideService.trackUserLocation for username : " + user.getUserName());

        VisitedLocationDTO visitedLocation = gpsProxy.getUserLocation(user.getUserId());
        user.addToVisitedLocations(modelConverter.toVisitedLocation(visitedLocation));

        rewardsService.calculateRewards(user);

        return visitedLocation;
    }

    /**
     * Tracks all user location asynchronously.
     *
     * @param users The user list
     */
    public void trackAllUserLocation(List<User> users) {
        LOGGER.debug("Inside TourGuideService.trackAllUserLocation");

        for (User user : users) {
            Runnable runnable = () -> {
                trackUserLocation(user);
            };
            executorService.execute(runnable);
        }
        shutdown();
    }

    /**
     * Calls TourGuideService's getUser method to retrieves the user with the given username, checks if user
     * has a least one visited location, if so converts the user last visited location to a dto object by
     * calling DTOConverter's toLocationDTO method. If not, gets the user location by calling GpsProxy's
     * getUserLocation method and add it to the user's visited locations history. Then, converts the Location
     * object to a dto object by calling DtoConverter's toLocationDTO method.
     *
     * @param userName Username of the user
     * @return The location of the user
     */
    public LocationDTO getUserLocation(final String userName) {
        LOGGER.debug("Inside TourGuideService.getUserLocation for username : " + userName);

        User user = getUser(userName);

        if (user.getVisitedLocations().size() > 0) {
            return dtoConverter.toLocationDTO(user.getLastVisitedLocation().getLocation());
        }

        return dtoConverter.toLocationDTO(trackUserLocation(user).getLocation());
    }

    /**
     * Calls TourGuideService's getAllUsers() method to retrieves the user list then Map each user's
     * id to the user's recent location by calling TourGuide's getUserLocation method.
     *
     * @return All user recent location map to their id
     */
    public Map<String, LocationDTO> getAllUserRecentLocation() {
        LOGGER.debug("Inside TourGuideService.getAllUserRecentLocation");

        return getAllUsers().stream().collect(Collectors.toMap(u -> u.getUserId().toString(),
                u -> getUserLocation(u.getUserName())));
    }

    /**
     * Calls TourGuideService's getUser method to retrieves the user with the given username, sums user rewards
     * points, then retrieves the user providers by calling tripDealsProxy's getProviders method. Each of the
     * provider is converted to a model object by calling ModelConverter's toProvider method and add to a list.
     *
     * @param userName Username of the user
     * @return The list of the user provider
     */
    public List<ProviderDTO> getUserTripDeals(final String userName) {
        LOGGER.debug("Inside TourGuideService.getUserTripDeals for username : " + userName);

        User user = getUser(userName);

        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(r -> r.getRewardPoints()).sum();

        List<ProviderDTO> providers = tripDealsProxy.getProviders(internalTestHelper.getTripPricerApiKey(),
                user.getUserId(), user.getUserPreferences().getNumberOfAdults(), user.getUserPreferences()
                        .getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulativeRewardPoints);

        List<Provider> providerList = new ArrayList<>();
        providers.forEach(provider -> {
            providerList.add(modelConverter.toProvider(provider));
        });
        user.setTripDeals(providerList);

        return providers;
    }

    /**
     * Calculates the distance between each attraction and the user location, then sorts distance values to get the
     * closest five tourist attractions to the user. Creates five NearByAttractionDTO object that contains the attraction
     * name, the attraction location, the user location, the distance calculated and the user reward points, then add `
     * them to a list. Finally, creates a RecommendedAttractionDTO object that contains the list of NearByAttractionDTO
     * and the user location.
     *
     * @param userName Username of the user
     * @return RecommendedAttractionDTO object that contains the five attractions closest to the user and the
     * user location.
     */
    public RecommendedAttractionDTO getUserRecommendedAttractions(final String userName) {
        LOGGER.debug("Inside TourGuideService.getUserRecommendedAttractions for username : " + userName);

        User user = getUser(userName);
        Location userLocation = modelConverter.toLocation(getUserLocation(userName));
        List<AttractionDTO> attractions = gpsProxy.getAttractions();

        Map<AttractionDTO, Double> attractionsMap = new HashMap<>();
        attractions.stream()
                .forEach(a -> {
                    attractionsMap.put(a, distanceCalculator.getDistanceInMiles(a.getLocation(), userLocation));
                });

        Map<AttractionDTO, Double> closestAttractionsMap = attractionsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List<NearByAttractionDTO> nearByAttractions = new ArrayList<>();
        closestAttractionsMap.entrySet().stream()
                .forEach(a -> {
                    nearByAttractions.add(new NearByAttractionDTO(a.getKey().getAttractionName(),
                            a.getKey().getLocation(), userLocation, a.getValue(),
                            rewardsProxy.getRewardPoints(a.getKey().getAttractionId(), user.getUserId())));
                });

        return new RecommendedAttractionDTO(userLocation, nearByAttractions);
    }

    /**
     * Shutdowns the ExecutorService and waits until all tasks complete their execution or the specified timeout
     * is reached.
     */
    public void shutdown() {
        LOGGER.debug("Inside TourGuideService.shutdown");

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
