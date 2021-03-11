package tourGuide.service;

import tourGuide.dto.*;
import tourGuide.model.user.User;

import java.util.List;
import java.util.Map;

public interface ITourGuideService {

    void addUser(final User user);

    User getUser(final String userName);

    UserPreferencesDTO getUserPreferences(final String userName);

    List<User> getAllUsers();

    List<UserRewardDTO> getUserRewards(final String userName);

    VisitedLocationDTO trackUserLocation(User user);

    void trackUserLocationWithThreads(final User user);

    LocationDTO getUserLocation(final String userName);

    ProviderListDTO getUserTripDeals(final String userName);

    RecommendedAttractionDTO getUserRecommendedAttractions(final String userName);

    Map<String, LocationDTO> getAllUserRecentLocation();

    void shutdown() throws InterruptedException;
}
