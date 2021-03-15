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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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


    @BeforeEach
    public void setUp() {

        userId = UUID.fromString("4b69b4d7-a783-49b3-9819-fee155c3e18c");
        providerDTO1 = new ProviderDTO("name1", 100, UUID.randomUUID());
        providerDTO2 = new ProviderDTO("name2", 200, UUID.randomUUID());

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("GetProviders")
    @DisplayName("Given an user preferences, when getProviders request, then return OK status")
    public void givenAnUserPreferences_whenGetProvidersRequest_thenReturnOKStatus() throws Exception {

        when(tripDealsService.getProviders("test-server-api-key", userId,1, 1, 2,
                1000)).thenReturn(Arrays.asList(providerDTO1, providerDTO2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tripDeals/providers/" +
                "test-server-api-key/4b69b4d7-a783-49b3-9819-fee155c3e18c/1/1/2/1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("name1");
        assertThat(content).contains("name2");
        verify(tripDealsService).getProviders("test-server-api-key", userId,1, 1, 2,
                1000);
    }
}
