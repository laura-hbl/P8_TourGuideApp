package tourGuide.dto;

import lombok.*;
import tourGuide.model.Location;

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
@NoArgsConstructor
public class VisitedLocationDTO {

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
