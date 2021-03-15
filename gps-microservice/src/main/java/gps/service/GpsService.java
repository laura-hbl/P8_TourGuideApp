package gps.service;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gps.util.DTOConverter;
import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GpsService implements IGpsService {

    private final GpsUtil gpsUtil;

    private final DTOConverter dtoConverter;

    @Autowired
    public GpsService(final GpsUtil gpsUtil, final DTOConverter dtoConverter) {
        this.gpsUtil = gpsUtil;
        this.dtoConverter = dtoConverter;
    }

    public VisitedLocationDTO getUserLocation(final UUID userId) {

        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);

        return dtoConverter.toVisitedLocationDTO(visitedLocation);
    }

    public List<AttractionDTO> getAttractions() {

        List<AttractionDTO> attractions = new ArrayList<>();

        gpsUtil.getAttractions().forEach(attraction -> attractions.add(dtoConverter.toAttractionDTO(attraction)));

        return attractions;
    }
}
