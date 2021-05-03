package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Permits the storage and retrieving data of a visited location.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
public class VisitedLocation {

    /**
     * Id of the user.
     */
    private UUID userId;

    /**
     * Gps coordinates of the visited location.
     */
    private Location location;

    /**
     * Visited Date.
     */
    private Date timeVisited;
}
