package tripDeals.integration;

import org.junit.jupiter.api.DisplayName;
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
public class TripDealsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URL
    private final static String PROVIDERS_URL = "/tripDeals/providers/test-server-api-key/4b69b4d7-a783-49b3-9819" +
            "-fee155c3e18c/1/1/2/1000";

    @Test
    @DisplayName("Given an user preferences, when getProviders request, then return OK status")
    public void givenAnUserPreferences_whenGetProvidersRequest_thenReturnOKStatus() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:" + port +
                PROVIDERS_URL, Object[].class);

        assertThat(response.getBody().length).isGreaterThan(0);
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Given invalid path variable type, when getProviders request, then return BadRequest status")
    public void givenInvalidPathVariableType_whenGetProvidersRequest_thenReturnBadRequestStatus() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:" + port +
                "/tripDeals/providers/test-server-api-key/4b69b4d7-a783-49b3-9819-fee155c3e18c/1/1/2/a", null);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Given a missing path variable, when getProviders request, then return BadRequest status")
    public void givenMissingUserIdPathVariable_whenGetProvidersRequest_thenReturnBadRequestStatus() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("http://localhost:" + port +
                "/tripDeals/providers/test-server-api-key/ /1/1/2/ ", null);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }
}
