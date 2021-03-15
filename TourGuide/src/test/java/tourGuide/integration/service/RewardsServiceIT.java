package tourGuide.integration.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tourGuide.dto.AttractionDTO;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;
import tourGuide.proxies.MicroserviceGpsProxy;
import tourGuide.proxies.MicroserviceRewardsProxy;
import tourGuide.service.RewardsService;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.ModelConverter;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class RewardsServiceIT {

    @Autowired
    private RewardsService rewardsService;

    @Autowired
    private MicroserviceGpsProxy gpsProxy;

    @Autowired
    private ModelConverter modelConverter;

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has visited one attraction, when calculateReward, then rewards should be equal to one")
    public void givenAnUserWithOneVisitedAttraction_whenCalculateRewards_thenUserRewardIsEqualToOne() {
        User user = new User(UUID.randomUUID(), "laura", "000", "laura@gmail.com");
        AttractionDTO attraction = gpsProxy.getAttractions().get(0);
        user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction.getLocation(), new Date()));

        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(1);
    }

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has not visited any attraction, when calculateReward, then rewards should be equal to zero")
    public void givenAnUserWithNoAttractionVisited_whenCalculateRewards_thenUserRewardIsEqualToZero() {
        User user = new User(UUID.randomUUID(), "laura", "000", "laura@gmail.com");

        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(0);
    }

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has visited twice the same attraction, when calculateReward, then rewards should be " +
            "equal to one")
    public void givenAnUserThatHasVisitedTwiceTheSameAttraction_whenCalculateRewards_thenUserRewardIsEqualToOne() {
        User user = new User(UUID.randomUUID(), "laura", "000", "laura@gmail.com");
        AttractionDTO attraction = gpsProxy.getAttractions().get(0);
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), attraction.getLocation(), new Date());
        user.addUserReward(new UserReward(visitedLocation, modelConverter.toAttraction(attraction), 100));
        user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction.getLocation(), new Date()));

        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(1);
    }

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has no visited location near an attraction, when calculateReward, then rewards should " +
            "be equal to zero")
    public void givenAnUserWithNoVisitedLocationNearAnAttraction_whenCalculateRewards_thenUserRewardIsEqualToZero() {
        User user = new User(UUID.randomUUID(), "laura", "000", "laura@gmail.com");
        gpsProxy.getAttractions().get(0).setLocation(new Location(117.922008, 33.817595));
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), new Location(-160.326003,
                -73.869629), new Date());
        user.addToVisitedLocations(visitedLocation);

        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(0);
    }

    @Test
    @Tag("CalculateRewardsWithThreads")
    @DisplayName("If user has visited one attraction, when calculateRewardsWithThreads, then rewards should be equal " +
            "to one")
    public void givenAnUserWithOneVisitedAttraction_whenCalculateRewardsWithThreads_thenUserRewardIsEqualToOne()
            throws InterruptedException {
        User user = new User(UUID.randomUUID(), "laura", "000", "laura@gmail.com");
        AttractionDTO attraction = gpsProxy.getAttractions().get(0);
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), attraction.getLocation(), new Date());
        user.addToVisitedLocations(visitedLocation);

        rewardsService.calculateRewards(user);
        TimeUnit.SECONDS.sleep(1);

        assertThat(user.getUserRewards().size()).isEqualTo(1);
    }
}
