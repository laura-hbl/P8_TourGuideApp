package tourGuide.dto;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPreferencesDTO {

    private String userName;

    private int attractionProximity = Integer.MAX_VALUE;

    private CurrencyUnit currency = Monetary.getCurrency("USD");

    private int lowerPricePoint = 0;

    private int highPricePoint = Integer.MAX_VALUE;

    private int tripDuration = 1;

    private int ticketQuantity = 1;

    private int numberOfAdults = 1;

    private int numberOfChildren = 0;

    public UserPreferencesDTO(final int attractionProximity, final int lowerPricePoint, final int highPricePoint,
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
