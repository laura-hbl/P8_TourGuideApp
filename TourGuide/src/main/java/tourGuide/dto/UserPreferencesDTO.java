package tourGuide.dto;

import javax.validation.constraints.Min;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

/**
 * Permits the storage and retrieving data of an user preferences.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class UserPreferencesDTO {

    /**
     * The radius of the area where attractions is considered as nearby one by the user.
     */
    @NumberFormat
    private int attractionProximity;

    /**
     * The user's preference on the lowest price limit.
     */
    @Min(value = 0, message = "Please enter a valid number")
    private int lowerPricePoint;

    /**
     * The user's preference on the highest price limit.
     */
    private int highPricePoint;

    /**
     * The user's travel preference for length of stay.
     */
    @Min(value = 1, message = "the length of stay must be at least 1")
    private int tripDuration;

    /**
     * The user's travel preference for ticket quantity.
     */
    @Min(value = 1, message = "the number of tickets must be at least 1")
    private int ticketQuantity;

    /**
     * The user's travel preference for the number of adults.
     */
    @Min(value = 1, message = "the number of adult must be at least 1")
    private int numberOfAdults;
    /**
     * The user's travel preference for the number of children.
     */
    @Min(value = 0, message = "Please enter a valid number")
    private int numberOfChildren;
}
