package tourGuide.integration;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tourGuide.dto.AttractionDTO;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.proxies.MicroserviceGpsProxy;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/performance-test.properties")
public class PerformanceIT {

    @Autowired
    private MicroserviceGpsProxy gpsProxy;

    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private TourGuideService tourGuideService;

    /*
     * A note on performance tests:
     *
     * The number of users generated for the high volume tests can be easily adjusted via test.user.numbers in
     * application-test.properties file.
     */

    @Test
    @Tag("TrackAllUserLocation")
    @DisplayName("Given a high volume users, when trackAllUserLocation, then elapsed time should be less or equal " +
            "than expected time")
    public void givenAHighVolumeUsers_whenTrackAllUserLocation_thenElapsedTimeShouldBeLessOrEqualThanExpectedTime() {
        List<User> allUsers = tourGuideService.getAllUsers();
        allUsers.forEach(u -> u.clearVisitedLocations());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        System.out.println("Tracking location start at : " + LocalDateTime.now());

        tourGuideService.trackAllUserLocation(allUsers);

        stopWatch.stop();

        System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
        allUsers.forEach(u -> assertTrue(u.getVisitedLocations().size() == 1));
    }

    @Test
    @Tag("CalculateAllRewards")
    @DisplayName("Given a high volume users that visited an attraction, when calculateAllRewards, then " +
            "elapsed time should be less or equal than expected time")
    public void givenHighVolumeUsers_whenCalculateAllRewards_thenElapsedTimeShouldBeLessOrEqualThanExpectedTime() {
        AttractionDTO attraction = gpsProxy.getAttractions().get(0);
        List<User> allUsers = tourGuideService.getAllUsers();

        allUsers.forEach(u -> {
            u.clearVisitedLocations();
            u.getUserRewards().clear();
            u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction.getLocation(), new Date()));
        });

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println("Tracking rewards start at : " + LocalDateTime.now());

        rewardsService.calculateAllRewards(allUsers);

        stopWatch.stop();

        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
        allUsers.forEach(u -> assertTrue(u.getUserRewards().size() == 1));
    }
}
