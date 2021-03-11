package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class VisitedLocation {

    private UUID userId;

    private Location location;

    private Date timeVisited;
}
