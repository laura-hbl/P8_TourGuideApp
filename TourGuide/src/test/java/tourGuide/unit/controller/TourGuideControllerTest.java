package tourGuide.unit.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tourGuide.controller.TourGuideController;
import tourGuide.dto.*;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.service.ITourGuideService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TourGuideController.class)
public class TourGuideControllerTest {

    @MockBean
    private ITourGuideService tourGuideService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private LocationDTO userLocationDTO;

    private Location userLocation;

    private Location attractionLocation;

    @BeforeEach
    public void setUp() {

        userLocationDTO = new LocationDTO(-160.326003, -73.869629);

        userLocation = new Location(-160.326003, -73.869629);

        attractionLocation = new Location(-117.922008, 33.817595);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an username, when getUserLocation request, then return OK status")
    public void givenAnUsername_whenGetUserLocationRequest_thenReturnOKStatus() throws Exception {
        when(tourGuideService.getUserLocation("Laura")).thenReturn(userLocationDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/location")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "Laura"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("-160.326003");
        assertThat(content).contains("-73.869629");
        verify(tourGuideService).getUserLocation(anyString());
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an empty username, when getUserLocation request, then return bad request status")
    public void givenAnEmptyUsername_whenGetUserLocationRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/location")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("getUsersRecentLocation()")
    @DisplayName("When getUsersRecentLocation request, then return OK status")
    public void WhenGetUsersRecentLocationRequest_thenReturnOKStatus() throws Exception {
        Map<String, LocationDTO> usersLocation = new HashMap<>();
        usersLocation.put(UUID.randomUUID().toString(), userLocationDTO);
        when(tourGuideService.getAllUserRecentLocation()).thenReturn(usersLocation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/locations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("-160.326003");
        assertThat(content).contains("-73.869629");
        verify(tourGuideService).getAllUserRecentLocation();
    }

    @Test
    @Tag("GetUserRecommendedAttractions")
    @DisplayName("Given an username, when getUserRecommendedAttractions request, then return OK status")
    public void givenAnUsername_whenGetUserRecommendedAttractionsRequest_thenReturnOKStatus() throws Exception {
        RecommendedAttractionDTO recommendedAttractionDTO = new RecommendedAttractionDTO(userLocation,
                Arrays.asList(new NearByAttractionDTO("Disney", attractionLocation, userLocation,
                        100.00, 25)));
        when(tourGuideService.getUserRecommendedAttractions("Laura")).thenReturn(recommendedAttractionDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/nearByAttractions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "Laura"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Disney");
        assertThat(content).contains("25");
        verify(tourGuideService).getUserRecommendedAttractions("Laura");
    }

    @Test
    @Tag("GetUserRecommendedAttractions")
    @DisplayName("Given an empty username, when getUserRecommendedAttractions request, then return bad request status")
    public void givenAnEmptyUsername_whenGetUserRecommendedAttractionsRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/nearByAttractions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("GetUserTripDeals")
    @DisplayName("Given an username, when getUserTripDeals request, then return OK status")
    public void givenAnUsername_whenGetUserTripDealsRequest_thenReturnOKStatus() throws Exception {
        ProviderListDTO providerListDTO = new ProviderListDTO();
        providerListDTO.setProviders(Arrays.asList(new ProviderDTO("providerName", 200, UUID.randomUUID())));

        when(tourGuideService.getUserTripDeals("Laura")).thenReturn(providerListDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/tripPricer")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "Laura"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("providerName");
        assertThat(content).contains("200");
        verify(tourGuideService).getUserTripDeals("Laura");
    }

    @Test
    @Tag("GetUserTripDeals")
    @DisplayName("Given an empty username, when getUserTripDeals request, then return bad request status")
    public void givenAnEmptyUsername_whenGetUserTripDealsRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/tripPricer")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("Given an username, when getUserRewards request, then return OK status")
    public void givenAnUsername_whenGetUserRewardsRequest_thenReturnOKStatus() throws Exception {
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), userLocation, new Date());
        Attraction attraction = new Attraction(UUID.randomUUID(), "Disney", "city", "state",
                attractionLocation);
        List<UserRewardDTO> userRewards = Arrays.asList(new UserRewardDTO(visitedLocation, attraction, 399));
        when(tourGuideService.getUserRewards("Laura")).thenReturn(userRewards);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "Laura"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("399");
        assertThat(content).contains("Disney");
        verify(tourGuideService).getUserRewards("Laura");
    }

    @Test
    @Tag("GetUserRewards")
    @DisplayName("Given an empty username, when getUserRewards request, then return bad request status")
    public void givenAnEmptyUsername_whenGetUserRewardsRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/rewards")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("GetUserPreferences")
    @DisplayName("Given an username, when getUserPreferences request, then return OK status")
    public void givenAnUsername_whenGetUserPreferencesRequest_thenReturnOKStatus() throws Exception {
        UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO(10, 100,
                300, 3, 2, 1, 1);

        when(tourGuideService.getUserPreferences("Laura")).thenReturn(userPreferencesDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/preferences")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", "Laura"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("300");
        verify(tourGuideService).getUserPreferences("Laura");
    }

    @Test
    @Tag("GetUserPreferences")
    @DisplayName("Given an empty username, when getUserPreferences request, then return bad request status")
    public void givenAnEmptyUsername_whenGetUserPreferencesRequest_thenReturnBadRequestStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/preferences")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName", ""))
                .andExpect(status().isBadRequest());
    }
}
