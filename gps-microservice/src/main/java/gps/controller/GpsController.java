package gps.controller;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.exception.ResourceNotFoundException;
import gps.service.IGpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Creates REST endpoints for operations on gps data.
 *
 * @author Laura Habdul
 * @see IGpsService
 */
@RestController
@RequestMapping("/gps")
public class GpsController {

    /**
     * IGpsService's implement class reference.
     */
    private IGpsService gpsService;

    /**
     * Constructor of class GpsController.
     * Initialize gpsService.
     *
     * @param gpsService IGpsService's implement class reference
     */
    @Autowired
    public GpsController(final IGpsService gpsService) {
        this.gpsService = gpsService;
    }

    /**
     * Retrieves the location of an user, checks if the location is not null, if not, returns the user location.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param userId id of the user to be located
     * @return The user location converted to a VisitedLocationDTO object
     */
    @GetMapping("/userLocation/{userId}")
    public VisitedLocationDTO getUserLocation(@PathVariable("userId") final UUID userId) {

        VisitedLocationDTO userLocation = gpsService.getUserLocation(userId);

        if (userLocation == null) {
            throw new ResourceNotFoundException("Failed to get user location");
        }

        return userLocation;
    }

    /**
     * Retrieves the list of all attractions referenced by the GpsUtil, checks if the attraction list is not empty,
     * if so, returns the attraction list. Else, ResourceNotFoundException is thrown.
     *
     * @return The attraction list
     */
    @GetMapping("/attractions")
    public List<AttractionDTO> getAttractions() {

        List<AttractionDTO> attractions = gpsService.getAttractions();

        if (attractions.isEmpty()) {
            throw new ResourceNotFoundException("Failed to get attraction list");
        }

        return attractions;
    }
}
