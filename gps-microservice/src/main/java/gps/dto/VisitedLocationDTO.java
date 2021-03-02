package gps.dto;

import gps.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class VisitedLocationDTO {

    private UUID userId;

    private Location location;

    private Date timeVisited;
}
