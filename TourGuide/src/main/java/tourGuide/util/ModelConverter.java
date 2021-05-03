package tourGuide.util;

import org.springframework.stereotype.Component;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.VisitedLocation;

/**
 * Allows the conversion of DTO class to Model class.
 *
 * @author Laura Habdul
 */
@Component
public class ModelConverter {

    /**
     * Converts VisitedLocationDTO to VisitedLocation.
     *
     * @param visitedLocationDTO VisitedLocationDTO object to convert
     * @return The VisitedLocation object
     */
    public VisitedLocation toVisitedLocation(final VisitedLocationDTO visitedLocationDTO) {

        return new VisitedLocation(visitedLocationDTO.getUserId(), visitedLocationDTO.getLocation(),
                visitedLocationDTO.getTimeVisited());
    }

    /**
     * Converts ProviderDTO to Provider.
     *
     * @param providerDTO ProviderDTO object to convert
     * @return The Provider object
     */
    public Provider toProvider(final ProviderDTO providerDTO) {

        return new Provider(providerDTO.getName(), providerDTO.getPrice(), providerDTO.getTripId());
    }

    /**
     * Converts AttractionDTO to Attraction.
     *
     * @param attractionDTO AttractionDTO object to convert
     * @return The Attraction object
     */
    public Attraction toAttraction(final AttractionDTO attractionDTO) {

        return new Attraction(attractionDTO.getAttractionId(), attractionDTO.getAttractionName(),
                attractionDTO.getCity(), attractionDTO.getState(), attractionDTO.getLocation());
    }

    /**
     * Converts LocationDTO to Location.
     *
     * @param locationDTO LocationDTO object to convert
     * @return The Location object
     */
    public Location toLocation(final LocationDTO locationDTO) {

        return new Location(locationDTO.getLatitude(), locationDTO.getLongitude());
    }
}
