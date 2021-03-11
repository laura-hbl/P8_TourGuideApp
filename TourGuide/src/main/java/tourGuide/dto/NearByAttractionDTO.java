package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tourGuide.model.Location;

@Data
@AllArgsConstructor
public class NearByAttractionDTO {

    private String attractionName;

    private Location attractionLocation;

    private Location userLocation;

    private double distance;

    private int rewardsPoints;
}
