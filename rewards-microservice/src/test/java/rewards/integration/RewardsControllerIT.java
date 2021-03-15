package rewards.integration;

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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URLS
    private final static String REWARDS_POINTS_URL = "/rewards/points/39fcd2f0-bc11-4f2a-8b90-f9123f01ec74/" +
            "4b69b4d7-a783-49b3-9819-fee155c3e18c";

    @Test
    @DisplayName("Given an userID and attractionID, when getRewardPoints request, then return OK status")
    public void givenAnUserIdAndAttractionId_whenGetRewardPointsRequest_thenReturnOkStatus() {
        ResponseEntity<Integer> response = restTemplate.getForEntity("http://localhost:" + port +
                REWARDS_POINTS_URL, Integer.class);

        assertNotNull(response);
        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
