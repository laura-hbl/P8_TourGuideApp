package gps.integration;

import gps.dto.VisitedLocationDTO;
import org.assertj.core.api.AssertionsForClassTypes;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GpsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URL
    private final static String ATTRACTIONS_URL = "/gps/attractions";

    private final static String USER_LOCATION_URL = "/gps/userLocation/";

    @Test
    @Tag("GET-Attractions")
    @DisplayName("When getAttractions request, then return OK status")
    public void whenGetAttractionsRequest_thenReturnOkStatus() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:" + port +
                ATTRACTIONS_URL, Object[].class);

        assertThat(response.getBody().length).isGreaterThan(0);
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-UserLocation")
    @DisplayName("Given an user id, when getUserLocation request, then return OK status")
    public void givenAnUserId_whenGetUserLocationRequest_thenReturnOkStatus() {
        ResponseEntity<VisitedLocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL + "4b69b4d7-a783-49b3-9819-fee155c3e18c", VisitedLocationDTO.class);

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-UserLocation")
    @DisplayName("Given an invalid id, when getUserLocation request, then return BadRequest status")
    public void givenAnInvalidId_whenGetUserLocationRequest_thenReturnBadRequestStatus() {
        ResponseEntity<VisitedLocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL + "4b69b4d7", VisitedLocationDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("GET-UserLocation")
    @DisplayName("Given missing user id path variable, when getUserLocation request, then return BadRequest status")
    public void givenMissingPathVariable_whenGetUserLocationRequest_thenReturnBadRequestStatus() {
        ResponseEntity<VisitedLocationDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                USER_LOCATION_URL + " ", VisitedLocationDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }
}
