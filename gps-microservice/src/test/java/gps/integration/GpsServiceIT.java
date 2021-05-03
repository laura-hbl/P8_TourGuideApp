package gps.integration;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.exception.ResourceNotFoundException;
import gps.service.GpsService;
import gps.util.DTOConverter;
import gpsUtil.GpsUtil;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GpsServiceIT {

    @Autowired
    private GpsService gpsService;

    @Autowired
    private GpsUtil gpsUtil;

    @Autowired
    private DTOConverter dtoConverter;

    @Test
    @Tag("GetUserLocation")
    @DisplayName("Given an user id, when getUserLocation, then return user location")
    public void givenAnUserId_whenGetUserLocation_thenReturnUserLocation() {

        UUID userId = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");
        VisitedLocationDTO visitedLocationDTO = gpsService.getUserLocation(userId);

        assertThat(visitedLocationDTO.getUserId()).isEqualTo(userId);
        assertThat(visitedLocationDTO.getLocation()).isNotNull();
    }

    @Test
    @Tag("GetAttractions")
    @DisplayName("when getAttractions, then return attractions")
    public void WhenGetAttractions_thenReturnAttractions() {

        List<AttractionDTO> attractionList = gpsService.getAttractions();

        assertThat(attractionList).isNotNull();
        assertThat(attractionList).isNotEmpty();
    }
}
