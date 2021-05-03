package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tourGuide.model.Location;

/**
 * Permits the storage and retrieving data of a near by attraction.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
public class NearByAttractionDTO {

    /**
     * The name of the attraction.
     */
    private String attractionName;

    /**
     * Gps coordinates of the attraction.
     */
    private Location attractionLocation;

    /**
     * Gps coordinates of the user.
     */
    private Location userLocation;

    /**
     * The distance between the user and the attraction.
     */
    private double distance;

    /**
     * Reward points acquired by the user after visiting the attraction.
     */
    private int rewardsPoints;
}
