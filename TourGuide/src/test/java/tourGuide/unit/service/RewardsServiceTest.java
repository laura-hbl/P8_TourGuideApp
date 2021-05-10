package tourGuide.unit.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tourGuide.dto.AttractionDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserReward;
import tourGuide.proxies.MicroserviceGpsProxy;
import tourGuide.proxies.MicroserviceRewardsProxy;
import tourGuide.service.RewardsService;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.ModelConverter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RewardsServiceTest {

    @InjectMocks
    private RewardsService rewardsService;

    @Mock
    private MicroserviceGpsProxy gpsProxy;

    @Mock
    private MicroserviceRewardsProxy rewardsProxy;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private DistanceCalculator distanceCalculator;

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has not visited any attraction, when CalculateReward, then rewards should be equal to zero")
    public void givenAnUserWithNoAttractionVisited_whenCalculateRewards_thenUserRewardIsEqualToZero() {
        User user = new User();
        Location location = new Location(-160.326003, -73.869629);
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), location, new Date());
        user.addToVisitedLocations(visitedLocation);

        Location attractionLocation = new Location(-117.922008, 33.817595);
        List<AttractionDTO> attractions = Arrays.asList(new AttractionDTO(UUID.randomUUID(), "name",
                "city", "state", attractionLocation));

        when(gpsProxy.getAttractions()).thenReturn(attractions);
        when(distanceCalculator.getDistanceInMiles(attractionLocation, location)).thenReturn(100.00);

        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(0);
        InOrder inOrder = inOrder(gpsProxy, distanceCalculator);
        inOrder.verify(gpsProxy).getAttractions();
        inOrder.verify(distanceCalculator).getDistanceInMiles(attractionLocation, location);
    }

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has any visitedLocation, when CalculateReward, then rewards should be equal to zero")
    public void givenAnUserWithNoVisitedLocation_whenCalculateRewards_thenUserRewardIsEqualToZero() {
        User user = new User();

        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(0);
    }

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has visited twice the same attraction, when CalculateReward, then rewards should be " +
            "equal to one")
    public void givenAnUserThatHasVisitedTwiceTheSameAttraction_whenCalculateRewards_thenUserRewardIsEqualToOne() {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        Location location = new Location(-160.326003, -73.869629);
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), location, new Date());
        user.addToVisitedLocations(visitedLocation);
        user.addToVisitedLocations(visitedLocation);

        Attraction attraction = new Attraction(UUID.randomUUID(), "name", "city", "state",
                location);
        List<AttractionDTO> attractions = Arrays.asList(new AttractionDTO(UUID.randomUUID(), "name",
                "city", "state", location));

        user.addUserReward(new UserReward(visitedLocation, attraction, 100));

        when(gpsProxy.getAttractions()).thenReturn(attractions);
        when(distanceCalculator.getDistanceInMiles(location, location)).thenReturn(8.00);
        when(distanceCalculator.getDistanceInMiles(location, location)).thenReturn(8.00);
        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(1);
        InOrder inOrder = inOrder(gpsProxy, distanceCalculator);
        inOrder.verify(gpsProxy).getAttractions();
        inOrder.verify(distanceCalculator, times(2)).getDistanceInMiles(location, location);
    }

    @Test
    @Tag("CalculateRewards")
    @DisplayName("If user has visited one attraction, when CalculateReward, then rewards should be equal to one")
    public void givenAnUserWithOneVisitedAttraction_whenCalculateRewards_thenUserRewardIsEqualToOne() {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        Location location = new Location(-160.326003, -73.869629);
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), location, new Date());
        user.addToVisitedLocations(visitedLocation);

        Location attractionLocation = new Location(-117.922008, 33.817595);
        AttractionDTO attractionDTO = new AttractionDTO(UUID.randomUUID(), "name",
                "city", "state", attractionLocation);
        Attraction attraction = new Attraction(UUID.randomUUID(), "name",
                "city", "state", attractionLocation);
        List<AttractionDTO> attractions = Arrays.asList(attractionDTO);

        when(gpsProxy.getAttractions()).thenReturn(attractions);
        when(distanceCalculator.getDistanceInMiles(any(Location.class), any(Location.class))).thenReturn(8.00);
        when(rewardsProxy.getRewardPoints(any(UUID.class), any(UUID.class))).thenReturn(200);
        when(modelConverter.toAttraction(attractionDTO)).thenReturn(attraction);

        rewardsService.calculateRewards(user);

        assertThat(user.getUserRewards().size()).isEqualTo(1);
        assertThat(user.getUserRewards().get(0).rewardPoints).isEqualTo(200);
        verify(rewardsProxy).getRewardPoints(any(UUID.class), any(UUID.class));
        verify(modelConverter).toAttraction(attractionDTO);
    }

    @Test
    @Tag("CalculateRewardAsync")
    @DisplayName("If user has visited one attraction, when calculateRewardAsync, then rewards should be equal to one")
    public void givenAnUserWithOneVisitedAttraction_whenCalculateRewardAsync_thenUserRewardIsEqualToOne() {
        User user = new User();
        user.setUserId(UUID.randomUUID());
        Location location = new Location(-160.326003, -73.869629);
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), location, new Date());
        user.addToVisitedLocations(visitedLocation);

        Location attractionLocation = new Location(-117.922008, 33.817595);
        AttractionDTO attractionDTO = new AttractionDTO(UUID.randomUUID(), "name",
                "city", "state", attractionLocation);
        Attraction attraction = new Attraction(UUID.randomUUID(), "name",
                "city", "state", attractionLocation);
        List<AttractionDTO> attractions = Arrays.asList(attractionDTO);

        when(gpsProxy.getAttractions()).thenReturn(attractions);
        when(distanceCalculator.getDistanceInMiles(any(Location.class), any(Location.class))).thenReturn(8.00);
        when(rewardsProxy.getRewardPoints(any(UUID.class), any(UUID.class))).thenReturn(200);
        when(modelConverter.toAttraction(attractionDTO)).thenReturn(attraction);

        rewardsService.calculateRewardAsync(user).join();

        assertThat(user.getUserRewards().size()).isEqualTo(1);
    }
}
