package gps.service;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;

import java.util.List;
import java.util.UUID;

public interface IGpsService {

    VisitedLocationDTO getUserLocation(final UUID userId);

    List<AttractionDTO> getAttractions();
}
