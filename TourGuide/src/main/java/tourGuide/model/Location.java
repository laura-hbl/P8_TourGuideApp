package tourGuide.model;

import lombok.*;

/**
 * Permits the storage and retrieving data of a location.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    /**
     * The location latitude.
     */
    private double latitude;

    /**
     * The location longitude.
     */
    private double longitude;
}
