package tourGuide.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.model.Provider;
import tourGuide.model.VisitedLocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Permits the storage and retrieving data of an user.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@NoArgsConstructor
public class User {

    /**
     * The id of the user.
     */
    private UUID userId;

    /**
     * The username of the user.
     */
    private String userName;

    /**
     * The phone number of the user.
     */
    private String phoneNumber;

    /**
     * The email of the user.
     */
    private String emailAddress;

    /**
     * The user last location date.
     */
    private Date latestLocationTimestamp;

    /**
     * The user visited location history.
     */
    private List<VisitedLocation> visitedLocations = new ArrayList<>();

    /**
     * The user reward list.
     */
    private List<UserReward> userRewards = new ArrayList<>();

    /**
     * The user preferences.
     */
    private UserPreferences userPreferences = new UserPreferences();

    /**
     * The user trip deals list.
     */
    private List<Provider> tripDeals = new ArrayList<>();

    /**
     * Constructor of class User.
     * Initializes userId, userName, phoneNumber and emailAddress.
     *
     * @param userId       The id of the user
     * @param userName     The username of the user
     * @param phoneNumber  The phone number of the user
     * @param emailAddress The email of the user
     */
    public User(final UUID userId, final String userName, final String phoneNumber, final String emailAddress) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * Adds a new Visited location to the user visited location history.
     */
    public void addToVisitedLocations(final VisitedLocation visitedLocation) {
        visitedLocations.add(visitedLocation);
    }

    /**
     * Clears all Visited location of the user.
     */
    public void clearVisitedLocations() {
        visitedLocations.clear();
    }

    /**
     * Adds a new reward to the user rewards list.
     */
    public void addUserReward(final UserReward userReward) {
        this.userRewards.add(userReward);
    }

    /**
     * Get the last visited location of the user.
     */
    public VisitedLocation getLastVisitedLocation() {
        return visitedLocations.get(visitedLocations.size() - 1);
    }
}