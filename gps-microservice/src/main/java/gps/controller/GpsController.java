package gps.controller;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.service.IGpsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gps")
public class GpsController {

    private Logger logger = LoggerFactory.getLogger(GpsController.class);

    private IGpsService gpsService;

    @Autowired
    public GpsController(final IGpsService gpsService) {
        this.gpsService = gpsService;
    }

    @GetMapping("/userLocation/{userId}")
    public VisitedLocationDTO getUserLocation(@PathVariable("userId") final UUID userId) {

        return gpsService.getUserLocation(userId);
    }

    @GetMapping("/attractions")
    public List<AttractionDTO> getAttractions() {

        return gpsService.getAttractions();
    }
}
