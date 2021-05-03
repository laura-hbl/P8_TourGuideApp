package tourGuide.model.user;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;

/**
 * Permits the storage and retrieving data of an user preferences.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@NoArgsConstructor
public class UserPreferences {

    /**
     * The radius of the area where attractions is considered as nearby one by the user.
     */
    private int attractionProximity = Integer.MAX_VALUE;

    /**
     * Defines US Dollar as used currency of the application.
     */
    private CurrencyUnit currency = Monetary.getCurrency("USD");

    /**
     * The user's preference on the lowest price limit.
     */
    private Money lowerPricePoint = Money.of(0, currency);

    /**
     * The user's preference on the highest price limit.
     */
    private Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);

    /**
     * The user's travel preference for length of stay.
     */
    private int tripDuration = 1;

    /**
     * The user's travel preference for ticket quantity.
     */
    private int ticketQuantity = 1;

    /**
     * The user's travel preference for the number of adults.
     */
    private int numberOfAdults = 1;

    /**
     * The user's travel preference for the number of children.
     */
    private int numberOfChildren = 0;

    /**
     * Constructor of class UserPreferences.
     * Initialize attractionProximity, lowerPricePoint, tripDuration,  ticketQuantity, numberOfAdults
     * and numberOfChildren.
     *
     * @param attractionProximity The radius of the area where attractions is considered as nearby one by the user
     * @param lowerPricePoint     The user's preference on the lowest price limit
     * @param highPricePoint      The user's preference on the highest price limit
     * @param tripDuration        The user's travel preference for length of stay
     * @param ticketQuantity      The user's travel preference for ticket quantity
     * @param numberOfAdults      The user's travel preference for the number of adults
     * @param numberOfChildren    The user's travel preference for the number of children
     */
    public UserPreferences(final int attractionProximity, final Money lowerPricePoint, final Money highPricePoint,
                           final int tripDuration, final int ticketQuantity, final int numberOfAdults,
                           final int numberOfChildren) {
        this.attractionProximity = attractionProximity;
        this.lowerPricePoint = lowerPricePoint;
        this.highPricePoint = highPricePoint;
        this.tripDuration = tripDuration;
        this.ticketQuantity = ticketQuantity;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
    }
}
