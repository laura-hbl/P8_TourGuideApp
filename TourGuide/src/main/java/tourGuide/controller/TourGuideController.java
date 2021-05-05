package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tourGuide.dto.*;
import tourGuide.exception.BadRequestException;
import tourGuide.service.ITourGuideService;

import java.util.List;
import java.util.Map;

/**
 * Creates REST endpoints for operations on user data.
 *
 * @author Laura Habdul
 * @see ITourGuideService
 */
@RestController
public class TourGuideController {

    /**
     * TourGuideController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(TourGuideController.class);

    /**
     * ITourGuideService's implement class reference.
     */
    private ITourGuideService tourGuideService;

    /**
     * Constructor of class TourGuideController.
     * Initialize tourGuideService.
     *
     * @param tourGuideService ITourGuideService's implement class reference.
     */
    @Autowired
    public TourGuideController(final ITourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }

    /**
     * Displays TourGuide home page.
     *
     * @return The greeting message
     */
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }

    /**
     * Retrieves the location of an user.
     *
     * @param userName username of the user to be located
     * @return The user location converted to a LocationDTO object
     */
    @GetMapping("/user/location")
    public LocationDTO getUserLocation(@RequestParam String userName) {
        LOGGER.debug("GET Request on /user/location with username : {}", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        LocationDTO userLocation = tourGuideService.getUserLocation(userName);

        LOGGER.debug("GET Request on /user/location - SUCCESS");
        return userLocation;
    }

    /**
     * Retrieves recent location of all users.
     *
     * @return All user recent location map to their id
     */
    @GetMapping("/users/locations")
    public Map<String, LocationDTO> getUsersRecentLocation() {
        LOGGER.debug("GET Request on /users/locations");

        Map<String, LocationDTO> usersRecentLocation = tourGuideService.getAllUserRecentLocation();

        LOGGER.debug("GET Request on /users/locations - SUCCESS");
        return usersRecentLocation;
    }

    /**
     * Retrieves the five attractions closest to the user.
     *
     * @param userName username of the user
     * @return A RecommendedAttractionDTO object that contains the five attractions closest to the user and the
     * user location
     */
    @GetMapping("/user/nearByAttractions")
    public RecommendedAttractionDTO getUserRecommendedAttractions(@RequestParam final String userName) {
        LOGGER.debug("GET Request on /user/nearByAttractions with username : {}", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        RecommendedAttractionDTO nearByAttractions = tourGuideService.getUserRecommendedAttractions(userName);

        LOGGER.debug("GET Request on /user/nearByAttractions - SUCCESS");
        return nearByAttractions;
    }

    /**
     * Retrieves the user trip deals.
     *
     * @param userName username of the user
     * @return The provider list
     */
    @GetMapping("/user/tripPricer")
    public List<ProviderDTO> getUserTripDeals(@RequestParam final String userName) {
        LOGGER.debug("GET Request on /user/tripPricer with username : {}", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        List<ProviderDTO> userTripDeals = tourGuideService.getUserTripDeals(userName);

        LOGGER.debug("GET Request on /user/tripPricer - SUCCESS");
        return userTripDeals;
    }

    /**
     * Retrieves the user rewards.
     *
     * @param userName username of the user
     * @return The list of the user rewards
     */
    @GetMapping("/user/rewards")
    public List<UserRewardDTO> getUserRewards(@RequestParam final String userName) {
        LOGGER.debug("GET Request on /user/rewards with username : {}", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        List<UserRewardDTO> userRewards = tourGuideService.getUserRewards(userName);

        LOGGER.debug("GET Request on /user/rewards - SUCCESS");
        return userRewards;
    }

    /**
     * Updates the user preferences.
     *
     * @param userPreferencesDTO the user preferences with updated data
     * @return The user preferences converted to an UserPreferencesDTO object
     */
    @PutMapping("/user/preferences")
    public String getUserPreferences(@Valid @RequestBody final UserPreferencesDTO userPreferencesDTO,
                                                                 @RequestParam final String userName) {
        LOGGER.debug("GET Request on /user/preferences with username : {}", userName);

        if (userName.length() == 0) {
            throw new BadRequestException("username is required");
        }
        UserPreferencesDTO userPreferences = tourGuideService.updateUserPreferences(userName, userPreferencesDTO);

        LOGGER.debug("GET Request on /user/preferences - SUCCESS");
        return JsonStream.serialize(userPreferences);
    }
}