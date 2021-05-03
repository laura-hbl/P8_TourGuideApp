package gps.util;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.model.Location;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of Model class to DTO class.
 *
 * @author Laura Habdul
 */
@Component
public class DTOConverter {

    /**
     * Converts DTOConverter empty constructor.
     */
    public DTOConverter() {
    }

    /**
     * Converts Attraction to AttractionDTO.
     *
     * @param attraction Attraction object to convert
     * @return The AttractionDTO object
     */
    public AttractionDTO toAttractionDTO(final Attraction attraction) {

        return new AttractionDTO(attraction.attractionId, attraction.attractionName, attraction.city,
                attraction.state, new Location(attraction.longitude, attraction.latitude));
    }

    /**
     * Converts VisitedLocation to VisitedLocationDTO.
     *
     * @param visitedLocation VisitedLocation object to convert
     * @return The VisitedLocationDTO object
     */
    public VisitedLocationDTO toVisitedLocationDTO(final VisitedLocation visitedLocation) {

        return new VisitedLocationDTO(visitedLocation.userId, new Location(visitedLocation.location.longitude,
                visitedLocation.location.latitude), visitedLocation.timeVisited);
    }
}
