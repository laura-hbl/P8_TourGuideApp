package tripDeals.util;

import lombok.NoArgsConstructor;
import tripDeals.dto.ProviderDTO;
import org.springframework.stereotype.Component;
import tripPricer.Provider;

/**
 * Allows the conversion of Model class to DTO class.
 *
 * @author Laura Habdul
 */
@Component
@NoArgsConstructor
public class DTOConverter {

    /**
     * Converts Provider to ProviderDTO.
     *
     * @param provider Provider object to convert
     * @return The ProviderDTO object
     */
    public ProviderDTO toProviderDTO(final Provider provider) {

        return new ProviderDTO(provider.name, provider.price, provider.tripId);
    }
}
