package tripDeals.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tripDeals.controller.TripDealsController;
import tripDeals.dto.ProviderDTO;
import tripDeals.util.DTOConverter;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TripDealsService implements ITripDealsService {

    private Logger logger = LoggerFactory.getLogger(TripDealsController.class);

    private final TripPricer tripPricer;

    private final DTOConverter dtoConverter;

    @Autowired
    public TripDealsService(final TripPricer tripPricer, final DTOConverter dtoConverter) {
        this.tripPricer = tripPricer;
        this.dtoConverter = dtoConverter;
    }

    public List<ProviderDTO> getProviders(final String apiKey, final UUID userId, int adults, int children,
                                          int nightsStay, int rewardPoints) {

        List<ProviderDTO> providers = new ArrayList<>();

        List<Provider> providerList = tripPricer.getPrice(apiKey, userId, adults, children, nightsStay, rewardPoints);

        if (providerList.isEmpty()) {
            logger.info("Sorry you don't have enough rewards points for any trip deal");
            return new ArrayList<>();
        }

        providerList.forEach(provider -> providers.add(dtoConverter.toProviderDTO(provider)));

        return providers;
    }
}
