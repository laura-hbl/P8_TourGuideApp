package tourGuide.service;

import tourGuide.dto.*;
import tourGuide.model.user.User;

import java.util.List;
import java.util.Map;

/**
 * TourGuideService interface.
 *
 * @author Laura Habdul
 */
public interface ITourGuideService {

    /**
     * Adds an user.
     *
     * @param user The user to be added
     */
    void addUser(final User user);

    /**
     * Retrieves the user with the given username.
     *
     * @param userName Username of the user to be found
     * @return The user found
     */
    User getUser(final String userName);

    /**
     * Updates preferences of the user.
     *
     * @param userName        Username of the user
     * @param userPreferences the user preferences with updated data
     * @return UserPreferences object that contains the user preferences updated
     */
    UserPreferencesDTO updateUserPreferences(final String userName, final UserPreferencesDTO userPreferences);

    /**
     * Retrieves the user list.
     *
     * @return The user list
     */
    List<User> getAllUsers();


    /**
     * Retrieves the user rewards.
     *
     * @param userName Username of the user
     * @return The list of the user rewards
     */
    List<UserRewardDTO> getUserRewards(final String userName);

    /**
     * Retrieves the user location.
     *
     * @param user The user to be located
     * @return The tracked location of the user
     */
    VisitedLocationDTO trackUserLocation(final User user);

    /**
     * Tracks all user location asynchronously.
     *
     * @param users The user list
     */
    void trackAllUserLocation(List<User> users);

    /**
     * Retrieves the location of the user with the given username.
     *
     * @param userName Username of the user
     * @return The location of the user
     */
    LocationDTO getUserLocation(final String userName);

    /**
     * Retrieves the trip deals of the user with the given username.
     *
     * @param userName Username of the user
     * @return The list of the user provider
     */
    List<ProviderDTO> getUserTripDeals(final String userName);

    /**
     * Retrieves the five attractions closest to the user.
     *
     * @param userName Username of the user
     * @return RecommendedAttractionDTO object that contains the five attractions closest to the user and the
     * user location.
     */
    RecommendedAttractionDTO getUserRecommendedAttractions(final String userName);

    /**
     * Retrieves all user recent location.
     *
     * @return All user recent location map to their username
     */
    Map<String, LocationDTO> getAllUserRecentLocation();

    /**
     * Shuts down the executor service.
     */
    void shutdown() throws InterruptedException;
}
