package gps.unit.service;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.service.GpsService;
import gps.util.DTOConverter;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GpsServiceTest {

    @InjectMocks
    private GpsService gpsService;

    @Mock
    private GpsUtil gpsUtil;

    @Mock
    private DTOConverter dtoConverter;

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an userID, when getUserLocation, then result should match expected VisitedLocationDTO")
    public void givenAnUserID_whenGetUserLocation_thenReturnExpectedVisitedLocationDTO() {
        UUID userID = UUID.randomUUID();
        Date date = new Date();
        Location location = new Location( -160.326003, -73.869629);
        VisitedLocation visitedLocation = new VisitedLocation(userID, location, date);
        VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(userID, location, date);

        when(gpsUtil.getUserLocation(userID)).thenReturn(visitedLocation);
        when(dtoConverter.toVisitedLocationDTO(visitedLocation)).thenReturn(visitedLocationDTO);

        VisitedLocationDTO result = gpsService.getUserLocation(userID);

        assertThat(result).isEqualToComparingFieldByField(visitedLocation);
        InOrder inOrder = inOrder(gpsUtil, dtoConverter);
        inOrder.verify(gpsUtil).getUserLocation(userID);
        inOrder.verify(dtoConverter).toVisitedLocationDTO(visitedLocation);
    }

    @Test
    @Tag("GetAttractions")
    @DisplayName("Given an attraction list, when getAttraction, then return expected attraction list")
    public void givenAnAttractionLIst_whenGetAttraction_thenReturnExpectedAttractionList() {
        Attraction attraction1 = new Attraction("name1", "city1" , "state1",
                -117.922008, 33.817595);
        Attraction attraction2 = new Attraction("name2", "city2" , "state2",
                -117.922008, 33.817595);

        AttractionDTO attraction1DTO = new AttractionDTO(attraction1.attractionId, "name1",
                "city1" , "state1", new Location(-117.922008, 33.817595));
        AttractionDTO attraction2DTO = new AttractionDTO(attraction2.attractionId, "name2",
                "city2" , "state2", new Location(-117.922008, 33.817595));
        List<AttractionDTO> attractionList = Arrays.asList(attraction1DTO, attraction2DTO);

        when(gpsUtil.getAttractions()).thenReturn(Arrays.asList(attraction1, attraction2));
        when(dtoConverter.toAttractionDTO(attraction1)).thenReturn(attraction1DTO);
        when(dtoConverter.toAttractionDTO(attraction2)).thenReturn(attraction2DTO);

        List<AttractionDTO> result = gpsService.getAttractions();

        assertThat(result).isEqualTo(attractionList);
        assertThat(result.size()).isEqualTo(2);
        InOrder inOrder = inOrder(gpsUtil, dtoConverter);
        inOrder.verify(gpsUtil).getAttractions();
        inOrder.verify(dtoConverter, times(2)).toAttractionDTO(any(Attraction.class));
    }
}
