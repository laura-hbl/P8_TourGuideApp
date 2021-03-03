package tripDeals.service;

import tripDeals.dto.ProviderDTO;

import java.util.List;
import java.util.UUID;

public interface ITripDealsService {

    List<ProviderDTO> getProviders(final String apiKey, final UUID userId, final int adults, final int children,
                                   final int nightsStay, final int rewardPoints);
}
