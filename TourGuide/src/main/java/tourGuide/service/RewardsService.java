package tourGuide.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class RewardsService implements IRewardsService {

    private Logger logger = LoggerFactory.getLogger(TourGuideService.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(400);

    private final MicroserviceGpsProxy gpsProxy;

    private final MicroserviceRewardsProxy rewardsProxy;

    private final ModelConverter modelConverter;

    private final DistanceCalculator distanceCalculator;

    @Autowired
    public RewardsService(final MicroserviceGpsProxy gpsProxy, final MicroserviceRewardsProxy rewardsProxy,
                          final ModelConverter modelConverter, final DistanceCalculator distanceCalculator) {
        this.gpsProxy = gpsProxy;
        this.rewardsProxy = rewardsProxy;
        this.modelConverter = modelConverter;
        this.distanceCalculator = distanceCalculator;
    }

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

    public void calculateRewardsWithThreads(final User user) {
        executorService.execute(new Runnable() {
            public void run() {
                calculateRewards(user);
            }
        });
    }

    private boolean isNearAttraction(final VisitedLocation visitedLocation, final Location attractionLocation) {
        return !(distanceCalculator.getDistanceInMiles(attractionLocation, visitedLocation.getLocation()) >
                ProximityBuffer.DEFAULT_PROXIMITY_BUFFER);
    }

    public void shutdown() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}
