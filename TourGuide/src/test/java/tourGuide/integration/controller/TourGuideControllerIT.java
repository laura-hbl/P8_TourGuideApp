package tourGuide.integration.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.RecommendedAttractionDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.model.user.User;
import tourGuide.service.TourGuideService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/integration-test.properties")
public class TourGuideControllerIT {

    @Autowired
    private TourGuideService tourGuideService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URL
    private final static String INDEX_URL = "/";
    private final static String USER_LOCATION_URL = "/user/location/";
    private final static String USERS_LOCATION_URL = "/users/locations";
    private final static String RECOMMENDED_ATTRACTIONS_URL = "/user/nearByAttractions/";
    private final static String USER_TRIP_DEALS_URL = "/user/tripPricer/";
    private final static String USER_REWARDS_URL = "/user/rewards/";
    private final static String USER_PREFERENCES_URL = "/user/preferences/";

    @Test
    @Tag("Index")
    @DisplayName("When index request, then return OK status")
    public void WhenIndexRequest_thenReturnOKStatus() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port +
               INDEX_URL, String.class);

        assertThat(response).isNotNull();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an username, when getUserLocation request, then return OK status")
    public void givenAnUsername_whenGetUserLocationRequest_thenReturnOKStatus() {
        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL + "?userName=internalUser1", LocationDTO.class);

        assertThat(response).isNotNull();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an user with no visited location history, when getUserLocation request, then return OK status")
    public void givenAnUserWithNoVisitedLocationHistory_whenGetUserLocationRequest_thenReturnOKStatus() {
        User user = tourGuideService.getUser("internalUser1");
        user.clearVisitedLocations();

        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL + "?userName=internalUser1", LocationDTO.class);

        assertThat(response).isNotNull();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an unregistered username, when getUserLocation request, then return OK status")
    public void givenAnUnregisteredUsername_whenGetUserLocationRequest_thenReturnOKStatus() {
        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL + "?userName=unregisteredUsername", LocationDTO.class);

        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an empty username, when getUserLocation request, then return BadRequest status")
    public void givenAnEmptyUsername_whenGetUserLocationRequest_thenReturnBadRequestStatus() {
        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL + "?userName=", null);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given missing parameter, when getUserLocation request, then return BadRequest status")
    public void givenMissingUsernameParameter_whenGetUserLocationRequest_thenReturnBadRequestStatus() {
        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL, null);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("getUsersRecentLocation")
    @DisplayName("When getUsersRecentLocation request, then return OK status")
    public void WhenGetUsersRecentLocationRequest_thenReturnOKStatus() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port +
                USERS_LOCATION_URL, String.class);

        assertThat(response).isNotNull();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserRecommendedAttractions")
    @DisplayName("Given an username, when getUserRecommendedAttractions request, then return OK status")
    public void givenAnUsername_whenGetUserRecommendedAttractionsRequest_thenReturnOKStatus() {
        ResponseEntity<RecommendedAttractionDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                RECOMMENDED_ATTRACTIONS_URL + "?userName=internalUser1", RecommendedAttractionDTO.class);

        assertThat(response).isNotNull();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserRecommendedAttractions")
    @DisplayName("Given an empty username, when getUserRecommendedAttractions request, then return BadRequest status")
    public void givenAnEmptyUsername_whenGetUserRecommendedAttractionsRequest_thenReturnBadRequestStatus() {
        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                RECOMMENDED_ATTRACTIONS_URL + "?userName=", null);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserTripDeals")
    @DisplayName("Given an username, when getUserTripDeals request, then return OK status")
    public void givenAnUsername_whenGetUserTripDealsRequest_thenReturnOKStatus() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_TRIP_DEALS_URL + "?userName=internalUser1", String.class);

        assertThat(response).isNotNull();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserTripDeals")
    @DisplayName("Given an empty username, when getUserTripDeals request, then return BadRequest status")
    public void givenAnEmptyUsername_whenGetUserTripDealsRequest_thenReturnBadRequestStatus() {
        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_TRIP_DEALS_URL + "?userName=", null);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("Given an username, when getUserRewards request, then return OK status")
    public void givenAnUsername_whenGetUserRewardsRequest_thenReturnOKStatus() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_REWARDS_URL + "?userName=internalUser1", String.class);

        assertThat(response).isNotNull();
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("Given an empty username, when getUserRewards request, then return BadRequest status")
    public void givenAnEmptyUsername_whenGetUserRewardsRequest_thenReturnBadRequestStatus() {
        ResponseEntity<LocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_REWARDS_URL + "?userName=", null);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("UpdateUserPreferences")
    @DisplayName("Given an user preferences to update, when updateUserPreferences request, then preference should be " +
            "updated correctly")
    public void givenAnUserPreferencesToUpdate_whenUpdateUserPreferencesRequest_thenPreferencesShouldBeUpdatedCorrectly() {
        UserPreferencesDTO userPreferencesToUpdate = new UserPreferencesDTO(217,
                0, 214, 2, 2, 1, 1);

        restTemplate.put("http://localhost:" + port +
                USER_PREFERENCES_URL + "?userName=internalUser1", userPreferencesToUpdate, UserPreferencesDTO.class);
        User user = tourGuideService.getUser("internalUser1");

        assertThat(user.getUserPreferences().getAttractionProximity()).isEqualTo(217);
        assertThat(user.getUserPreferences().getTripDuration()).isEqualTo(2);
        assertThat(user.getUserPreferences().getNumberOfChildren()).isEqualTo(1);
    }

    @Test
    @Tag("UpdateUserPreferences")
    @DisplayName("Given an empty username, when updateUserPreferences request, then user preferences is not updated")
    public void givenAnEmptyUsername_whenUpdateUserPreferencesRequest_thenUserPreferencesIsNotUpdated() {
        UserPreferencesDTO userPreferencesToUpdate = new UserPreferencesDTO(217,
                0, 214, 2, 2, 1, 1);

        restTemplate.put("http://localhost:" + port +
                USER_PREFERENCES_URL + "?userName=", userPreferencesToUpdate, UserPreferencesDTO.class);

        User user = tourGuideService.getUser("internalUser1");

        assertThat(user.getUserPreferences().getAttractionProximity()).isNotEqualTo((217));
        assertThat(user.getUserPreferences().getAttractionProximity()).isEqualTo(Integer.MAX_VALUE);
    }
}
