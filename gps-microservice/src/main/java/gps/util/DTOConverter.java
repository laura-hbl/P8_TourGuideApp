package gps.util;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {

    public DTOConverter() {
    }

    public AttractionDTO toAttractionDTO(final Attraction attraction) {

        return new AttractionDTO(attraction.attractionId, attraction.attractionName, attraction.city,
                attraction.state, new Location(attraction.latitude, attraction.longitude));
    }

    public VisitedLocationDTO toVisitedLocationDTO(final VisitedLocation visitedLocation) {

        return new VisitedLocationDTO(visitedLocation.userId, new Location(visitedLocation.location.latitude,
                visitedLocation.location.longitude), visitedLocation.timeVisited);
    }
}
