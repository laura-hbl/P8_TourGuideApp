package tourGuide.unit.service;

import javax.money.Monetary;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tourGuide.dto.*;
import tourGuide.exception.DataAlreadyRegisteredException;
import tourGuide.exception.ResourceNotFoundException;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.User;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.UserReward;
import tourGuide.proxies.MicroServiceTripDealsProxy;
import tourGuide.proxies.MicroserviceGpsProxy;
import tourGuide.proxies.MicroserviceRewardsProxy;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.util.DTOConverter;
import tourGuide.util.DistanceCalculator;
import tourGuide.util.ModelConverter;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TourGuideServiceTest {

    @InjectMocks
    private TourGuideService tourGuideService;

    @Mock
    private MicroserviceGpsProxy gpsProxy;

    @Mock
    private MicroServiceTripDealsProxy tripDealsProxy;

    @Mock
    private MicroserviceRewardsProxy rewardsProxy;

    @Mock
    private RewardsService rewardsService;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private DistanceCalculator distanceCalculator;

    @Mock
    private InternalTestHelper internalTestHelper;

    private User user1;

    private User user2;

    private List<User> userList;

    private Map<String, User> internalUser;

    private UserPreferences userPreferences;

    private UserPreferencesDTO userPreferencesDTO;

    private VisitedLocation visitedLocation;

    private VisitedLocationDTO visitedLocationDTO;

    private Attraction attraction;

    private UserRewardDTO userRewardDTO;

    private UserReward userReward;

    @Before
    public void setUp() {

        user1 = new User(UUID.randomUUID(), "Laura", "000", "laura@gmail.com");
        user2 = new User(UUID.randomUUID(), "Luc", "001", "luc@gmail.com");

        userPreferences = new UserPreferences(10,
                Money.of(100, Monetary.getCurrency("USD")),
                Money.of(300, Monetary.getCurrency("USD")), 3,
                2, 1, 1);
        userPreferencesDTO = new UserPreferencesDTO(10, 100,
                300, 3, 2, 1, 1);

        visitedLocation = new VisitedLocation(user1.getUserId(), new Location(-160.326003,
                -73.869629), new Date());
        visitedLocationDTO = new VisitedLocationDTO(user1.getUserId(), new Location(-160.326003,
                -73.869629), new Date());

        attraction = new Attraction(UUID.randomUUID(), "Disneyland" , "Anaheim" ,
                "CA" , new Location(-117.922008, 33.817595));

        userRewardDTO = new UserRewardDTO(visitedLocation, attraction, 300);
        userReward = new UserReward(visitedLocation, attraction, 300);

        internalUser = new HashMap<>();
        internalUser.put("Laura", user1);
        internalUser.put("Luc", user2);

        userList = Arrays.asList(user1, user2);
    }

    @Test
    @Tag("AddUser")
    @DisplayName("Given an user, when addUser, then user should be added correctly")
    public void givenAnUnUser_whenAddUser_thenUserShouldBeAddedCorrectly() {
        internalUser.clear();
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);

        tourGuideService.addUser(user2);

        assertThat(tourGuideService.getAllUsers()).contains(user2);
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("AddUser")
    @DisplayName("If the user's username is already used, when AddUser, then throw DataAlreadyRegisteredException")
    public void givenAnUserWithAnAlreadyUsedUsername_whenAddUser_thenDataAlreadyRegisteredExceptionIsThrown() {
        User user = new User(UUID.randomUUID(), "Laura", "000", "laura@gmail.com");
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);

        tourGuideService.addUser(user);
    }

    @Test
    @Tag("GetUser")
    @DisplayName("Given an user username, when getUser, then expected user should be returned correctly")
    public void givenAnUsername_whenGetUser_thenExpectedUserShouldBeReturnCorrectly() {
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);

        User userFound = tourGuideService.getUser("Laura");

        assertThat(userFound).isEqualToComparingFieldByField(user1);
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetUser")
    @DisplayName("If user can't be found, when getUser, then throw ResourceNotFoundException")
    public void givenAnUnregisteredUser_whenGetUser_thenResourceNotFoundExceptionIsThrown() {
        when(internalTestHelper.getInternalUserMap()).thenReturn(new HashMap<>());

        tourGuideService.getUser("Herve");
    }

    @Test
    @Tag("GetAllUser")
    @DisplayName("Given an user list, when getAllUser, then result should match expected user list")
    public void givenAnUserList_whenGetAllUser_thenReturnExpectedUserList() {
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);

        List<User> users = tourGuideService.getAllUsers();

        assertThat(users).isEqualTo(userList);
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetAllUser")
    @DisplayName("Given an empty user list, when getAllUser, then throw ResourceNotFoundException")
    public void givenAnEmptyUserList_whenGetAllUser_thenResourceNotFoundExceptionIsThrown() {
        internalUser.clear();

        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);

        tourGuideService.getAllUsers();
    }

    @Test
    @Tag("GetUserPreferences")
    @DisplayName("Given an username, when getUserPreferences, then result should match expected user preferences")
    public void givenAnUsername_whenGetUserPreferences_thenReturnExpectedUserPreferences() {
        user1.setUserPreferences(userPreferences);
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(dtoConverter.toUserPreferencesDTO(user1.getUserPreferences())).thenReturn(userPreferencesDTO);

        UserPreferencesDTO result = tourGuideService.getUserPreferences("Laura");

        assertThat(result).isEqualToComparingFieldByField(userPreferencesDTO);
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("Given an user that has a reward, when getUserRewards, then result should match expected user reward")
    public void givenAnUserWithAReward_whenGetUserRewards_thenReturnExpectedUserReward() {
        user1.addUserReward(userReward);
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(dtoConverter.toUserRewardDTO(any(UserReward.class))).thenReturn(userRewardDTO);

        List<UserRewardDTO> result = tourGuideService.getUserRewards("Laura");

        assertThat(result).contains(userRewardDTO);
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("Given an user with no reward, when getUserRewards, then result should be empty")
    public void givenAnUserWithNoRewards_whenGetUserRewards_thenResultShouldBeEmpty() {
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);

        List<UserRewardDTO> result = tourGuideService.getUserRewards("Laura");

        assertThat(result).isEmpty();
    }

    @Test
    @Tag("TrackUserLocation")
    @DisplayName("Given an user, when trackUserLocation, then return expected visited location")
    public void givenAnUser_whenTrackUserLocation_thenReturnExpectedUserLocation() {
        when(gpsProxy.getUserLocation(any(UUID.class))).thenReturn(visitedLocationDTO);
        when(modelConverter.toVisitedLocation(any(VisitedLocationDTO.class))).thenReturn(visitedLocation);

        VisitedLocationDTO result = tourGuideService.trackUserLocation(user1);

        assertThat(result).isEqualToComparingFieldByField(visitedLocationDTO);
        assertThat(user1.getVisitedLocations().get(0)).isEqualToComparingFieldByField(visitedLocationDTO);
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an user with no visited location, when getUserLocation, then return expected user location")
    public void givenAnUserWithNoVisitedLocation_whenGetUserLocation_thenReturnExpectedUserLocation() {

        LocationDTO expectedLocation = new LocationDTO(-160.326003, -73.869629);
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(gpsProxy.getUserLocation(any(UUID.class))).thenReturn(visitedLocationDTO);
        when(modelConverter.toVisitedLocation(any(VisitedLocationDTO.class))).thenReturn(visitedLocation);
        when(dtoConverter.toLocationDTO(any(Location.class))).thenReturn(expectedLocation);
        LocationDTO result = tourGuideService.getUserLocation(user1.getUserName());

        assertThat(result).isEqualToComparingFieldByField(expectedLocation);
        assertThat(user1.getVisitedLocations().size()).isEqualTo(1);
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an user with an expected visited location, when getUserLocation, then return expected " +
            "visited location")
    public void givenAnUserWithAnExpectedVisitedLocation_whenGetUserLocation_thenReturnExpectedUserLocation() {
        user1.addToVisitedLocations(visitedLocation);
        LocationDTO expectedLocation = new LocationDTO(-160.326003, -73.869629);
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(dtoConverter.toLocationDTO(any(Location.class))).thenReturn(expectedLocation);

        LocationDTO result = tourGuideService.getUserLocation(user1.getUserName());

        assertThat(result).isEqualToComparingFieldByField(expectedLocation);
        assertThat(user1.getVisitedLocations().size()).isEqualTo(1);
    }

    @Test
    @Tag("GetAllUserRecentLocation")
    @DisplayName("When getAllUserRecentLocation, then return expected users location")
    public void whenGetAllUserRecentLocation_thenReturnExpectedUsersLocation() {
        user1.addToVisitedLocations(visitedLocation);
        user2.addToVisitedLocations(new VisitedLocation(user2.getUserId(), new Location(-117.922008,
                33.817595), new Date()));
        LocationDTO user1Location = new LocationDTO(-160.326003, -73.869629);
        LocationDTO user2Location = new LocationDTO(-117.922008, 33.817595);
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(dtoConverter.toLocationDTO(user1.getLastVisitedLocation().getLocation())).thenReturn(user1Location);
        when(dtoConverter.toLocationDTO(user2.getLastVisitedLocation().getLocation())).thenReturn(user2Location);

        Map<String, LocationDTO> result = tourGuideService.getAllUserRecentLocation();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.values()).contains(user1Location, user2Location);
    }

    @Test
    @Tag("GetUserTripDeals")
    @DisplayName("Given an username, when getUserTripDeals, then return expected user trip deals")
    public void givenAnUsername_whenGetUserTripDeals_thenReturnExpectedUserTripDeals() {
        user1.addUserReward(userReward);
        ProviderDTO providerDTO = new ProviderDTO("name", 100, UUID.randomUUID());
        Provider provider = new Provider("name", 100, UUID.randomUUID());
        ProviderListDTO providerListDTO = new ProviderListDTO();
        providerListDTO.setProviders(Arrays.asList(providerDTO));
        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(tripDealsProxy.getProviders(anyString(), any(UUID.class), anyInt(), anyInt(), anyInt(), anyInt()))
                .thenReturn(providerListDTO);
        when(modelConverter.toProvider(any(ProviderDTO.class))).thenReturn(provider);

        ProviderListDTO providerList = tourGuideService.getUserTripDeals(user1.getUserName());

        assertThat(providerList.getProviders()).contains(providerDTO);
        assertThat(user1.getTripDeals()).contains(provider);
    }

    @Test
    @Tag("GetUserRecommendedAttractions")
    @DisplayName("Given an username, when getUserRecommendedAttractions, then return expected users recommended attractions")
    public void givenAnUsername_whenGetUserRecommendedAttractions_thenReturnExpectedRecommendedAttractions() {
       user1.addToVisitedLocations(visitedLocation);
        Location userLocation = user1.getLastVisitedLocation().getLocation();
        UUID userID = user1.getUserId();
        AttractionDTO attraction1 = new AttractionDTO(UUID.randomUUID(), "Disneyland", "Anaheim", "CA", new Location(33.817595D, -117.922008D));
        AttractionDTO attraction2 = new AttractionDTO(UUID.randomUUID(), "Jackson Hole", "Jackson Hole", "WY", new Location(43.582767D, -110.821999D));
        AttractionDTO attraction3 = new AttractionDTO(UUID.randomUUID(), "Mojave","Kelso", "CA", new Location(35.141689D, -115.510399D));
        AttractionDTO attraction4 = new AttractionDTO(UUID.randomUUID(), "Kartchner Caverns", "Benson", "AZ", new Location(31.837551D, -110.347382D));
        AttractionDTO attraction5 = new AttractionDTO(UUID.randomUUID(),"Joshua Tree", "Joshua Tree", "CA", new Location(33.881866D, -115.90065D));
        AttractionDTO attraction6 = new AttractionDTO(UUID.randomUUID(),"Buffalo", "St Joe", "AR", new Location(35.985512D, -92.757652D));
        AttractionDTO attraction7 = new AttractionDTO(UUID.randomUUID(),"Hot Springs", "Hot Springs", "AR", new Location(34.52153D, -93.042267D));
        List<AttractionDTO> attractions = Arrays.asList(attraction1, attraction2, attraction3, attraction4, attraction5, attraction6, attraction7);

        when(internalTestHelper.getInternalUserMap()).thenReturn(internalUser);
        when(gpsProxy.getAttractions()).thenReturn(attractions);
        when(distanceCalculator.getDistanceInMiles(attraction1.getLocation(),userLocation)).thenReturn(100.00);
        when(distanceCalculator.getDistanceInMiles(attraction2.getLocation(),userLocation)).thenReturn(200.00);
        when(distanceCalculator.getDistanceInMiles(attraction3.getLocation(),userLocation)).thenReturn(300.00);
        when(distanceCalculator.getDistanceInMiles(attraction4.getLocation(),userLocation)).thenReturn(400.00);
        when(distanceCalculator.getDistanceInMiles(attraction5.getLocation(),userLocation)).thenReturn(500.00);
        when(distanceCalculator.getDistanceInMiles(attraction6.getLocation(),userLocation)).thenReturn(600.00);
        when(distanceCalculator.getDistanceInMiles(attraction7.getLocation(),userLocation)).thenReturn(700.00);

        when(rewardsProxy.getRewardPoints(attraction1.getAttractionId(), userID)).thenReturn(100);
        when(rewardsProxy.getRewardPoints(attraction2.getAttractionId(), userID)).thenReturn(500);
        when(rewardsProxy.getRewardPoints(attraction3.getAttractionId(), userID)).thenReturn(700);
        when(rewardsProxy.getRewardPoints(attraction4.getAttractionId(), userID)).thenReturn(900);
        when(rewardsProxy.getRewardPoints(attraction5.getAttractionId(), userID)).thenReturn(300);

        RecommendedAttractionDTO result = tourGuideService.getUserRecommendedAttractions(user1.getUserName());

        assertThat(result.getNearbyAttractions()).isNotEmpty();
        assertThat(result.getNearbyAttractions().size()).isEqualTo(5);
        assertThat(result.getNearbyAttractions().get(0).getAttractionName()).isEqualTo("Disneyland");
        assertThat(result.getNearbyAttractions().get(1).getAttractionName()).isEqualTo("Jackson Hole");
        assertThat(result.getNearbyAttractions().get(2).getAttractionName()).isEqualTo("Mojave");
        assertThat(result.getNearbyAttractions().get(3).getAttractionName()).isEqualTo("Kartchner Caverns");
        assertThat(result.getNearbyAttractions().get(4).getAttractionName()).isEqualTo("Joshua Tree");
    }
}

