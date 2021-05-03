package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Permits the storage and retrieving data of an attraction.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
public class Attraction {

    /**
     * Id of the attraction.
     */
    private UUID attractionId;

    /**
     * Name of the attraction.
     */
    private String attractionName;

    /**
     * City where the attraction is located.
     */
    private String city;

    /**
     * State where the attraction is located.
     */
    private String state;

    /**
     * Gps coordinates of the attraction.
     */
    private Location location;
}
