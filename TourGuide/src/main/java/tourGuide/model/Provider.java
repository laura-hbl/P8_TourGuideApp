package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Permits the storage and retrieving data of a provider.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
public class Provider {

    /**
     * The name of the attraction.
     */
    private String name;

    /**
     * Trip deals price of the provider.
     */
    private double price;

    /**
     * The id of the provider.
     */
    private UUID tripId;
}
