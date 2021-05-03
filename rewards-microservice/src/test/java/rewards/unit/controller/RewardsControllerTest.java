package rewards.unit.controller;

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
import rewards.controller.RewardsController;
import rewards.service.IRewardsService;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

    @MockBean
    private IRewardsService rewardsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private UUID userId;

    private UUID attractionId;

    @BeforeEach
    public void setUp() {

        userId = UUID.fromString("39fcd2f0-bc11-4f2a-8b90-f9123f01ec74");
        attractionId = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("GetAttractionRewardPoints")
    @DisplayName("Given valid id, when getAttractionRewardPoints request, then return ok status")
    public void givenValidId_whenGetAttractionRewardPoints_thenReturnOkStatus() throws Exception {
        when(rewardsService.getAttractionRewardPoints(attractionId, userId)).thenReturn(100);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rewards/points/" + attractionId
                + "/" + userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("100");
        verify(rewardsService).getAttractionRewardPoints(attractionId, userId);
    }

    @Test
    @Tag("GetAttractionRewardPoints")
    @DisplayName("Given invalid id, when getAttractionRewardPoints request, then return BadRequest status")
    public void givenInvalidUserId_whenGetAttractionRewardPointsRequest_thenReturnBadRequestStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rewards/points/" + attractionId
                + "/4b69b4d7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Invalid UUID string");
    }

    @Test
    @Tag("GetAttractionRewardPoints")
    @DisplayName("Given missing user id path variable, when getAttractionRewardPoints request, then return BadRequest status")
    public void givenMissingUserIdPathVariable_whenGetAttractionRewardPointsRequest_thenReturnBadRequestStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rewards/points/" + attractionId
                + "/ ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("userId parameter is missing");
    }
}

