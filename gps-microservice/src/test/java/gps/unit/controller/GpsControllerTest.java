package gps.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gps.controller.GpsController;
import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.service.IGpsService;
import gpsUtil.location.Location;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    private List<AttractionDTO> attractions;

    private UUID userId;


    @BeforeEach
    public void setUp() {

        AttractionDTO attraction1 = new AttractionDTO(UUID.randomUUID(), "name1",
                "city1" , "state1", new Location(-117.922008, 33.817595));
        AttractionDTO attraction2 = new AttractionDTO(UUID.randomUUID(), "name2",
                "city2" , "state2", new Location(-117.922008, 33.817595));
        attractions = Arrays.asList(attraction1, attraction2);

        userId = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an userID, when getUserLocation request, then return OK status")
    public void givenAnUserID_whenGetUserLocationRequest_thenReturnOKStatus() throws Exception {
        VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(userId, new Location( -160.326003,
                -73.869629), new Date());
        when(gpsService.getUserLocation(userId)).thenReturn(visitedLocationDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/gps/userLocation/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains(userId.toString());
        verify(gpsService).getUserLocation(userId);
    }

    @Test
    @Tag("GetAttractions")
    @DisplayName("Given an attraction list ,when getAttractions request, then return OK status")
    public void givenAnAttractionList_whenGetAttractionsRequest_thenReturnOKStatus() throws Exception {
        when(gpsService.getAttractions()).thenReturn(attractions);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/gps/attractions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("name1");
        assertThat(content).contains("name2");
        verify(gpsService).getAttractions();
    }
}
