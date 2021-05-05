package gps.service;

import gps.controller.GpsController;
import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.util.DTOConverter;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Contains methods that deals with Gps business logic.
 *
 * @author Laura Habdul
 */
@Service
public class GpsService implements IGpsService {

    /**
     * GpsService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(GpsService.class);

    /**
     * GpsUtil instance.
     */
    private final GpsUtil gpsUtil;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * Constructor of class GpsService.
     * Initialize gpsUtil and dtoConverter.
     *
     * @param gpsUtil      GpsUtil instance.
     * @param dtoConverter DTOConverter instance.
     */
    @Autowired
    public GpsService(final GpsUtil gpsUtil, final DTOConverter dtoConverter) {
        this.gpsUtil = gpsUtil;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Calls GpsUtil's getUserLocation method to retrieves the user location with the given id, then converts
     * the given VisitedLocation object to a dto object by calling DTOConverter's toVisitedLocationDTO method.
     *
     * @param userId the user id to be located
     * @return The user visited location converted to a VisitedLocationDTO object
     */
    public VisitedLocationDTO getUserLocation(final UUID userId) {
        LOGGER.debug("Inside GpsService.getUserLocation");

        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);

        return dtoConverter.toVisitedLocationDTO(visitedLocation);
    }

    /**
     * Calls GpsUtil's getAttractions method to retrieves the list of all attractions, converts each attraction
     * to a dto object by calling DTOConverter's toAttractionDTO method then collect them to a List.
     *
     * @return The attraction list
     */
    public List<AttractionDTO> getAttractions() {
        LOGGER.debug("Inside GpsService.getAttractions");

        List<AttractionDTO> attractionList = new ArrayList<>();
        List<Attraction> attractions = gpsUtil.getAttractions();

        attractions.forEach(attraction -> {
            attractionList.add(dtoConverter.toAttractionDTO(attraction));
        });

        return attractionList;
    }
}
