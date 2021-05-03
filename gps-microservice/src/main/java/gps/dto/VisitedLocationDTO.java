package gps.dto;

import gps.model.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Permits the storage and retrieving data of a location2 visited by an user.
 *
 * @author Laura Habdul
 */
@Getter
@Setter
@AllArgsConstructor
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
