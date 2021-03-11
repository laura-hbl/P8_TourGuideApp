package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tourGuide.model.Location;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttractionDTO {

    private UUID attractionId;

    private String attractionName;

    private String city;

    private String state;

    private Location location;
}

