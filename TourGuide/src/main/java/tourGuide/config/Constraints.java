package tourGuide.config;

/**
 * Contains the different validator constraints.
 *
 * @author Laura Habdul
 */
public class Constraints {

    /**
     * Empty constructor of class Constraints.
     */
    public Constraints() {
    }

    /**
     * Minimum number allowed for the user's travel preference for length of stay.
     */
    public static final int TRIP_DURATION_MIN_VALUE = 1;

    /**
     * Minimum number allowed for the user's travel preference for ticket quantity.
     */
    public static final int TICKET_QUANTITY_MIN_VALUE = 1;

    /**
     * Minimum value allowed the user's travel preference for the number of adults.
     */
    public static final int ADULT_NUMBER_MIN_VALUE = 1;

    /**
     * Minimum value allowed for the user's travel preference for the number of children.
     */
    public static final int CHILD_NUMBER_MIN_VALUE = 0;

    /**
     * Minimum value allowed for the user's preference on the lowest price limit.
     */
    public static final int LOWER_PRICE_VALUE = 0;

    /**
     * Minimum value allowed for the radius of the area where attractions is considered as nearby one by the user.
     */
    public static final int LOWER_PROXIMITY = 0;
}
