package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tourGuide.model.Location;

import java.util.List;

/**
 * Permits the storage and retrieving data of a recommended attraction.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
public class RecommendedAttractionDTO {

    /**
     * Gps coordinates of the user.
     */
    private Location userLocation;

    /**
     * The list of user's near by attractions.
     */
    private List<NearByAttractionDTO> nearbyAttractions;
}
