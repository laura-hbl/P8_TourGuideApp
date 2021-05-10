package tourGuide.integration.service;

import javax.money.Monetary;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tourGuide.dto.*;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.ResourceNotFoundException;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.UserReward;
import tourGuide.service.TourGuideService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/integration-test.properties")
public class TourGuideServiceIT {

    @Autowired
    private TourGuideService tourGuideService;

    @Test
    @Tag("AddUser")
    @DisplayName("Given an user, when addUser, then user should be added correctly")
    public void givenAnUnUser_whenAddUser_thenUserShouldBeAddedCorrectly() {
        UUID userID = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");
        User user = new User(userID, "Laura", "000", "laura@gmail.com");
        tourGuideService.addUser(user);

        assertThat(tourGuideService.getAllUsers()).contains(user);
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("AddUser")
    @DisplayName("If the user's username is already used, when AddUser, then throw DataAlreadyRegisteredException")
    public void givenAnUserWithAnAlreadyUsedUsername_whenAddUser_thenDataAlreadyRegisteredExceptionIsThrown() {
        UUID userID = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");
        User user = new User(userID, "internalUser1", "000", "usera@gmail.com");

        tourGuideService.addUser(user);
    }

    @Test
    @Tag("GetUser")
    @DisplayName("Given an username, when getUser, then the user should be returned correctly")
    public void givenAnUsername_whenGetUser_thenTheUserShouldBeReturnCorrectly() {
        User user = tourGuideService.getUser("internalUser1");

        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("internalUser1");

    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetUser")
    @DisplayName("If user is not registered, when getUser, then throw ResourceNotFoundException")
    public void givenAnUnregisteredUser_whenGetUser_thenResourceNotFoundExceptionIsThrown() {
        tourGuideService.getUser("Herve");
    }

    @Test
    @Tag("GetAllUser")
    @DisplayName("When getAllUser, then return user list")
    public void WhenGetAllUser_thenReturnUserList() {
        List<User> users = tourGuideService.getAllUsers();

        assertThat(users).isNotEmpty();
        assertThat(users).isNotNull();
    }

    @Test
    @Tag("UpdateUserPreferences")
    @DisplayName("Given an , when updateUserPreferences, then user preferences")
    public void givenAn_whenUpdateUserPreferences_thenUserPreferences() {
        UserPreferencesDTO userPreferences = new UserPreferencesDTO(10, 100,
                300, 3, 2, 4, 1);

        UserPreferencesDTO result = tourGuideService.updateUserPreferences("internalUser1", userPreferences);

        assertThat(result).isNotNull();
        assertThat(result).isEqualToComparingFieldByField(userPreferences);
    }

    @Test
    @Tag("TrackUserLocation")
    @DisplayName("Given an user, when trackUserLocation, then the user location should be added to history")
    public void givenAnUser_whenTrackUserLocation_thenUserLocationShouldBeAddedToHistory() {
        UUID userID = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");
        User user = new User(userID, "internalUser1", "000", "user@gmail.com");

        tourGuideService.trackUserLocation(user).join();

        assertThat(user.getVisitedLocations().size()).isEqualTo(1);
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("If user has a reward, when getUserRewards, then return user reward")
    public void givenAnUserWithAReward_whenGetUserRewards_thenReturnUserReward() {
        User user = tourGuideService.getUser("internalUser1");
        user.getUserRewards().clear();
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), new Location(-160.326003,
                -73.869629), new Date());
        Attraction attraction = new Attraction(UUID.randomUUID(), "Disneyland" , "Anaheim" ,
                "CA" , new Location(-117.922008, 33.817595));
        UserReward userReward = new UserReward(visitedLocation, attraction, 300);
        user.addUserReward(userReward);

        List<UserRewardDTO> result = tourGuideService.getUserRewards("internalUser1");

        assertThat(result).isNotNull();
        assertThat(result.get(0).getRewardPoints()).isEqualTo(300);
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("If user has no reward, when getUserRewards, then result should be empty")
    public void givenAnUserWithNoRewards_whenGetUserRewards_thenResultShouldBeEmpty() {
        User user = tourGuideService.getUser("internalUser2");
        user.getUserRewards().clear();
        List<UserRewardDTO> result = tourGuideService.getUserRewards("internalUser2");

        assertThat(result).isEmpty();
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an user with no visited location, when getUserLocation, then return user location")
    public void givenAnUserWithNoVisitedLocation_whenGetUserLocation_thenReturnUserLocation() {
        User user = tourGuideService.getUser("internalUser1");
        user.getVisitedLocations().clear();

        LocationDTO result = tourGuideService.getUserLocation("internalUser1");

        assertThat(result).isNotNull();
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an user with one visited location, when getUserLocation, then return expected the " +
            "visited location")
    public void givenAnUserWithOneVisitedLocation_whenGetUserLocation_thenReturnTheVisitedLocation() {
        User user = tourGuideService.getUser("internalUser1");
        Location location = new Location(-160.326003, -73.869629);
        user.getVisitedLocations().clear();
        VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), location, new Date());
        user.addToVisitedLocations(visitedLocation);

        LocationDTO result = tourGuideService.getUserLocation("internalUser1");

        assertThat(result).isNotNull();
        assertThat(result).isEqualToComparingFieldByField(location);
    }

    @Test
    @Tag("GetAllUserRecentLocation")
    @DisplayName("When getAllUserRecentLocation, then return users recent location")
    public void whenGetAllUserRecentLocation_thenReturnUsersLocation() {
        List<User> users = tourGuideService.getAllUsers();

        Map<String, LocationDTO> result = tourGuideService.getAllUserRecentLocation();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(users.size());
        result.values().forEach(r -> assertNotNull(r));
    }

    @Test
    @Tag("GetUserTripDeals")
    @DisplayName("Given an username, when getUserTripDeals, then return user trip deals")
    public void givenAnUsername_whenGetUserTripDeals_thenReturnUserTripDeals() {
        User user = tourGuideService.getUser("internalUser1");
        user.setUserPreferences(new UserPreferences(10, Money.of(100,
                Monetary.getCurrency("USD")), Money.of(300, Monetary.getCurrency("USD")),
                3, 2, 1, 1));

        List<ProviderDTO> result = tourGuideService.getUserTripDeals("internalUser1");

        assertThat(result).isNotEmpty();
    }

    @Test
    @Tag("GetUserRecommendedAttractions")
    @DisplayName("Given an username, when getUserRecommendedAttractions, then return the closest five attractions")
    public void givenAnUsername_whenGetUserRecommendedAttractions_thenReturnTheFiveClosestAttractions() {
        RecommendedAttractionDTO result = tourGuideService.getUserRecommendedAttractions("internalUser1");

        assertThat(result.getNearbyAttractions()).isNotEmpty();
        assertThat(result.getNearbyAttractions().size()).isEqualTo(5);
    }

    @Test
    @Tag("GetUserRecommendedAttractions")
    @DisplayName("Given an user with no visited location, when getUserRecommendedAttractions, then return the " +
            "closest five attractions")
    public void givenAnUserWithNoVisitedLocations_whenGetUserRecommendedAttractions_thenReturnTheFiveClosestAttractions() {
        User user = tourGuideService.getUser("internalUser1");
        user.clearVisitedLocations();
        RecommendedAttractionDTO result = tourGuideService.getUserRecommendedAttractions("internalUser1");

        assertThat(result.getNearbyAttractions()).isNotEmpty();
        assertThat(result.getNearbyAttractions().size()).isEqualTo(5);
    }
}
