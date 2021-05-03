package tripDeals.unit.controller;

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
import tripDeals.controller.TripDealsController;
import tripDeals.dto.ProviderDTO;
import tripDeals.service.ITripDealsService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TripDealsController.class)
public class TripDealsControllerTest {

    @MockBean
    private ITripDealsService tripDealsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private UUID userId;

    private ProviderDTO providerDTO1;

    private ProviderDTO providerDTO2;

    private List<ProviderDTO> providers;

    // URL
    private final static String PROVIDERS_URL = "/tripDeals/providers/test-server-api-key/4b69b4d7-a783-49b3-9819-" +
            "fee155c3e19c/1/1/2/100";

    @BeforeEach
    public void setUp() {

        userId = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e19c");
        providerDTO1 = new ProviderDTO("name1", 100, UUID.randomUUID());
        providerDTO2 = new ProviderDTO("name2", 200, UUID.randomUUID());

        providers = Arrays.asList(providerDTO1, providerDTO2);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("GetProviders")
    @DisplayName("Given an user preferences, when getProviders request, then return OK status")
    public void givenAnUserPreferences_whenGetProvidersRequest_thenReturnOKStatus() throws Exception {
        when(tripDealsService.getProviders(anyString(), any(UUID.class), anyInt(), anyInt(), anyInt(),
                anyInt())).thenReturn(providers);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PROVIDERS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("name1");
        assertThat(content).contains("name2");
        verify(tripDealsService).getProviders(anyString(), any(UUID.class), anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    @Tag("GetProviders")
    @DisplayName("Given invalid path variable type, when getProviders request, then return BadRequest status")
    public void givenInvalidPathVariableType_whenGetProvidersRequest_thenReturnBadRequestStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tripDeals/providers/" +
                "test-server-api-key/4b69b4d7-a783-49b3-9819-fee155c3e19c/1/1/2/a")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Required type for rewardPoints is type: int");
    }

    @Test
    @Tag("GetProviders")
    @DisplayName("Given a missing path variable, when getProviders request, then return BadRequest status")
    public void givenMissingUserIdPathVariable_whenGetProvidersRequest_thenReturnBadRequestStatus() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tripDeals/providers/" +
                "test-server-api-key/ /1/1/2/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("userId parameter is missing");
    }

    @Test
    @Tag("GetProviders")
    @DisplayName("Given an empty provider list, when getProviders request, then return NotFound status")
    public void givenAnEmptyProviderList_whenGetProvidersRequest_thenReturnNotFoundStatus() throws Exception {
        when(tripDealsService.getProviders(anyString(), any(UUID.class), anyInt(), anyInt(), anyInt(),
                anyInt())).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PROVIDERS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Failed to get provider list");
    }
}