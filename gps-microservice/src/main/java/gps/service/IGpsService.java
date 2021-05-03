package gps.service;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;

import java.util.List;
import java.util.UUID;

/**
 * GpsService interface.
 *
 * @author Laura Habdul
 */
public interface IGpsService {

    /**
     * Retrieves the location of an user based on the given id.
     *
     * @param userId the user id to be located
     * @return The user visited location converted to a VisitedLocationDTO object
     */
    VisitedLocationDTO getUserLocation(final UUID userId);

    /**
     * Retrieves the list of all attractions referenced by the GpsUtil.
     *
     * @return The attraction list
     */
    List<AttractionDTO> getAttractions();
}
