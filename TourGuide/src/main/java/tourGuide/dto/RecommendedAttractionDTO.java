package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import tourGuide.model.Location;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendedAttractionDTO {

    private Location userLocation;

    private List<NearByAttractionDTO> nearbyAttractions;
}
