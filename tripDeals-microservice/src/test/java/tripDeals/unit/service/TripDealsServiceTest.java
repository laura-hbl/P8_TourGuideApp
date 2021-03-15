package tripDeals.unit.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tripDeals.dto.ProviderDTO;
import tripDeals.service.TripDealsService;
import tripDeals.util.DTOConverter;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripDealsServiceTest {

    @InjectMocks
    private TripDealsService tripDealsService;

    @Mock
    private TripPricer tripPricer;

    @Mock
    private DTOConverter dtoConverter;

    @Test
    @Tag("GetProviders")
    @DisplayName("Given an user preferences, when getProviders, then result should match expected Providers list")
    public void givenAnUserPreferences_whenGetProviders_thenReturnExpectedProvidersList() {
        UUID userID = UUID.randomUUID();
        UUID tripID1 = UUID.randomUUID();
        UUID tripID2 = UUID.randomUUID();
        Provider provider1 = new Provider(tripID1, "name1", 100);
        Provider provider2 = new Provider(tripID1, "name2", 200);

        ProviderDTO providerDTO1 = new ProviderDTO("name1", 100, tripID1);
        ProviderDTO providerDTO2 = new ProviderDTO("name2", 200, tripID2);

        when(tripPricer.getPrice("apiKey", userID, 2, 2, 2, 400))
                .thenReturn(Arrays.asList(provider1, provider2));
        when(dtoConverter.toProviderDTO(provider1)).thenReturn(providerDTO1);
        when(dtoConverter.toProviderDTO(provider2)).thenReturn(providerDTO2);

        List<ProviderDTO> result = tripDealsService.getProviders("apiKey", userID, 2, 2,
                2, 400);

        assertThat(result).isEqualTo(Arrays.asList(providerDTO1, providerDTO2));
        assertThat(result.size()).isEqualTo(2);
        InOrder inOrder = inOrder(tripPricer, dtoConverter);
        inOrder.verify(tripPricer).getPrice("apiKey", userID, 2, 2, 2, 400);
        inOrder.verify(dtoConverter, times(2)).toProviderDTO(any(Provider.class));
    }
}
