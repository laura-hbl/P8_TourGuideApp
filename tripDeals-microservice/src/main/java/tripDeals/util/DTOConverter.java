package tripDeals.util;

import tripDeals.dto.ProviderDTO;
import org.springframework.stereotype.Component;
import tripPricer.Provider;

@Component
public class DTOConverter {

    public DTOConverter() {
    }

    public ProviderDTO toProviderDTO(final Provider provider) {

        return new ProviderDTO(provider.name, provider.price, provider.tripId);
    }
}
