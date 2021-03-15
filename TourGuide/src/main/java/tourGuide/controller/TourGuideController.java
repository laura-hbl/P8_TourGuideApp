package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.dto.ProviderListDTO;
import tourGuide.dto.RecommendedAttractionDTO;
import tourGuide.exception.BadRequestException;
import tourGuide.service.ITourGuideService;

@RestController
public class TourGuideController {

    private Logger logger = LoggerFactory.getLogger(TourGuideController.class);

    private ITourGuideService tourGuideService;

    @Autowired
    public TourGuideController(final ITourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }

    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    @GetMapping("/user/location")
    public String getUserLocation(@RequestParam String userName) {

        if (userName == null || userName.length() == 0) {
            throw new BadRequestException("username is required");
        }

        return JsonStream.serialize(tourGuideService.getUserLocation(userName));
    }
    @GetMapping("/users/locations")
    public String getUsersRecentLocation() {

        return JsonStream.serialize(tourGuideService.getAllUserRecentLocation());
    }

    @GetMapping("/user/nearByAttractions")
    public String getUserRecommendedAttractions(@RequestParam final String userName) {

        if (userName == null || userName.length() == 0) {
            throw new BadRequestException("username is required");
        }

        return JsonStream.serialize(tourGuideService.getUserRecommendedAttractions(userName));
    }

    @GetMapping("/user/tripPricer")
    public String getUserTripDeals(@RequestParam final String userName) {

        if (userName == null || userName.length() == 0) {
            throw new BadRequestException("username is required");
        }

        return JsonStream.serialize(tourGuideService.getUserTripDeals(userName));
    }

    @GetMapping("/user/rewards")
    public String getUserRewards(@RequestParam final String userName) {

        if (userName == null || userName.length() == 0) {
            throw new BadRequestException("username is required");
        }

        return JsonStream.serialize(tourGuideService.getUserRewards(userName));
    }

    @GetMapping("/user/preferences")
    public String getUserPreferences(@RequestParam String userName) {

        if (userName == null || userName.length() == 0) {
            throw new BadRequestException("username is required");
        }

        return JsonStream.serialize(tourGuideService.getUserPreferences(userName));
    }
}