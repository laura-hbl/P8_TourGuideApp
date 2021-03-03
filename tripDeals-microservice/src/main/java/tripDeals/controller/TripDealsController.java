package tripDeals.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripDeals.dto.ProviderDTO;
import tripDeals.dto.ProviderListDTO;
import tripDeals.service.ITripDealsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tripDeals")
public class TripDealsController {

    private Logger logger = LoggerFactory.getLogger(TripDealsController.class);

    private ITripDealsService tripDealsService;

    @Autowired
    public TripDealsController(final ITripDealsService tripDealsService) {
        this.tripDealsService = tripDealsService;
    }

    @RequestMapping("/")
    public String index() {
        return "TripDeals home page!";
    }

    @GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardsPoints}")
    ProviderListDTO getProviders(@PathVariable final String apiKey, @PathVariable final UUID userId,
                                 @PathVariable final int adults, @PathVariable final int children,
                                 @PathVariable final int nightsStay, @PathVariable final int rewardsPoints) {

        return new ProviderListDTO(tripDealsService.getProviders(apiKey, userId, adults, children, nightsStay,
                rewardsPoints));

    }
}
