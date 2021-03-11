package tourGuide.service;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tourGuide.dto.*;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
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

@Service
public class TourGuideService {

	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);

	private final ExecutorService executorService = Executors.newFixedThreadPool(400);

	private final MicroserviceGpsProxy gpsProxy;

	private final MicroserviceRewardsProxy rewardsProxy;

	private final MicroServiceTripDealsProxy tripDealsProxy;

	private final RewardsService rewardsService;

	private final ModelConverter modelConverter;

	private final DTOConverter dtoConverter;

	private final DistanceCalculator distanceCalculator;

	private final InternalTestHelper internalTestHelper;

	public Tracker tracker;

	@Value("${test.mode.enabled}")
	private boolean isTestMode;

	@Value("${gps.integration.test.enabled}")
	private boolean isIntegrationTest;

	@Autowired
	public TourGuideService(final MicroserviceGpsProxy gpsProxy, final MicroserviceRewardsProxy rewardsProxy,
							final MicroServiceTripDealsProxy tripDealsProxy, final RewardsService rewardsService,
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

	@PostConstruct
	public void initialization() {

		if (isTestMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			internalTestHelper.initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		if (!isIntegrationTest) {
			this.tracker = new Tracker(this);
			tracker.startTracking();
		}

	}

	public void addUser(final User user) {
		if (!internalTestHelper.getInternalUserMap().containsKey(user.getUserName())) {
			internalTestHelper.getInternalUserMap().put(user.getUserName(), user);
		}
	}

	public User getUser(final String userName) {
		return internalTestHelper.getInternalUserMap().get(userName);
	}

	public List<User> getAllUsers() {

		return internalTestHelper.getInternalUserMap().values().stream().collect(Collectors.toList());
	}

	public UserPreferencesDTO getUserPreferences(final String userName) {

		User user = getUser(userName);

		UserPreferencesDTO userPreferences = dtoConverter.toUserPreferencesDTO(user.getUserPreferences());
		userPreferences.setUserName(userName);

		return userPreferences;
	}

	public List<UserRewardDTO> getUserRewards(final String userName) {

		User user = getUser(userName);

		List<UserRewardDTO> userRewards = new ArrayList<>();
		user.getUserRewards().stream().forEach(reward -> userRewards.add(dtoConverter.toUserRewardDTO(reward)));

		return userRewards;
	}

	public VisitedLocationDTO trackUserLocation(final User user) {
		VisitedLocationDTO visitedLocation = gpsProxy.getUserLocation(user.getUserId());
		user.addToVisitedLocations(modelConverter.toVisitedLocation(visitedLocation));
		rewardsService.calculateRewards(user);

		return visitedLocation;
	}

	public void trackUserLocationWithThreads(final User user) {
		executorService.execute(new Runnable() {
			public void run() {
				trackUserLocation(user);
			}
		});
	}

	public LocationDTO getUserLocation(final String userName) {

		User user = getUser(userName);

		if (user.getVisitedLocations().size() > 0) {
			return dtoConverter.toLocationDTO(user.getLastVisitedLocation().getLocation());
		}

		VisitedLocation userLocation = modelConverter.toVisitedLocation(gpsProxy.getUserLocation(user.getUserId()));
		user.addToVisitedLocations(userLocation);

		return dtoConverter.toLocationDTO(userLocation.getLocation());
	}

	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				tracker.stopTracking();
			}
		});
	}

	public Map<String, LocationDTO> getAllUserRecentLocation() {

		return getAllUsers().stream().collect(Collectors.toMap(u -> u.getUserId().toString(),
				u -> dtoConverter.toLocationDTO(u.getLastVisitedLocation().getLocation())));
	}

	public void shutdown() throws InterruptedException {
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

}
