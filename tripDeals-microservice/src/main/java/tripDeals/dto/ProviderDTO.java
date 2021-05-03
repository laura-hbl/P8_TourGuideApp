package tripDeals.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class ProviderDTO {

    /**
     * The provider name.
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
