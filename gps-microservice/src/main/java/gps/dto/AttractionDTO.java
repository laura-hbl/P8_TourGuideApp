package gps.dto;

import gps.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AttractionDTO {

    private UUID attractionId;

    private String attractionName;

    private String city;

    private String state;

    private Location location;
}

