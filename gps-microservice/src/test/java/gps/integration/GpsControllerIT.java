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

    private final static String ATTRACTIONS_URL = "/gps/attractions";

    @Test
    @Tag("GET-Attractions")
    @DisplayName("When GetAttractions request, then return OK status")
    public void whenGetAttractionsRequest_thenReturnOkStatus() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:" + port +
                ATTRACTIONS_URL, Object[].class);

        assertThat(response.getBody().length).isGreaterThan(0);
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
