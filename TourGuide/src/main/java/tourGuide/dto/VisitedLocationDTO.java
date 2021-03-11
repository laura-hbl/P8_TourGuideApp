package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tourGuide.model.Location;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitedLocationDTO {

    private UUID userId;

    private Location location;

    private Date timeVisited;
}
