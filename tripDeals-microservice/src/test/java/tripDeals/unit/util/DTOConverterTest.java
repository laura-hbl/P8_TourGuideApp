package tripDeals.unit.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tripDeals.dto.ProviderDTO;
import tripDeals.util.DTOConverter;
import tripPricer.Provider;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @Tag("Valid")
    @DisplayName("Given a Provider, when ToProviderDTO, then result should match expected ProviderDTO")
    public void givenAProvider_whenToProviderDTO_thenReturnExpectedProviderDTO() {
        UUID tripId = UUID.randomUUID();
        ProviderDTO providerDTO = new ProviderDTO("name", 200, tripId);

        ProviderDTO result = dtoConverter.toProviderDTO(new Provider(tripId, "name", 200));

        assertThat(result).isEqualToComparingFieldByField(providerDTO);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null Provider, then toProviderDTO should raise an NullPointerException")
    public void givenANullProvider_whenToProviderDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toProviderDTO(null));
    }
}
