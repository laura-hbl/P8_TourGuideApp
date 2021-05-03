package gps.unit.controller;

import gps.controller.GpsController;
import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.model.Location;
import gps.service.IGpsService;
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

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(GpsController.class)
public class GpsControllerTest {

    @MockBean
    private IGpsService gpsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    // URL
    private final static String ATTRACTIONS_URL = "/gps/attractions";

    private final static String USER_LOCATION_URL = "/gps/userLocation/";

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an userID, when getUserLocation request, then return OK status")
    public void givenAnUserID_whenGetUserLocationRequest_thenReturnOKStatus() throws Exception {
        UUID userId = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");
        VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(userId, new Location( -160.326003,
                -73.869629), new Date());
        when(gpsService.getUserLocation(userId)).thenReturn(visitedLocationDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_LOCATION_URL + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains(userId.toString());
        verify(gpsService).getUserLocation(userId);
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given missing path variable, when getUserLocation request, then return BadRequest status")
    public void givenMissingPathVariable_whenGetUserLocationRequest_thenReturnBadRequestStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_LOCATION_URL + " ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("userId parameter is missing");
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given and invalid id, when getUserLocation request, then return BadRequest status")
    public void givenAnInvalidId_whenGetUserLocationRequest_thenReturnBadRequestStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_LOCATION_URL + "4b69b4d7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Invalid UUID string");
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an null user location, when getUserLocation request, then return NotFound status")
    public void givenAnNullUserLocation_whenGetUserLocationRequest_thenReturnNotFoundStatus() throws Exception {
        UUID userId = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");

        when(gpsService.getUserLocation(userId)).thenReturn(null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_LOCATION_URL + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Failed to get user location");
    }

    @Test
    @Tag("GetAttractions")
    @DisplayName("Given an attraction list ,when getAttractions request, then return OK status")
    public void givenAnAttractionList_whenGetAttractionsRequest_thenReturnOKStatus() throws Exception {
        AttractionDTO attraction1 = new AttractionDTO(UUID.randomUUID(), "name1",
                "city1" , "state1", new Location(-117.922008, 33.817595));
        AttractionDTO attraction2 = new AttractionDTO(UUID.randomUUID(), "name2",
                "city2" , "state2", new Location(-117.922008, 33.817595));
        List<AttractionDTO> attractions = Arrays.asList(attraction1, attraction2);
        when(gpsService.getAttractions()).thenReturn(attractions);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ATTRACTIONS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("name1");
        assertThat(content).contains("name2");
        verify(gpsService).getAttractions();
    }

    @Test
    @Tag("GetAttractions")
    @DisplayName("Given an empty attraction list ,when getAttractions request, then return NotFound status")
    public void givenAnEmptyAttractionList_whenGetAttractionsRequest_thenReturnNotFoundStatus() throws Exception {
        when(gpsService.getAttractions()).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ATTRACTIONS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Failed to get attraction list");
    }
}
