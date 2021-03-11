package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Attraction {

    private UUID attractionId;

    private String attractionName;

    private String city;

    private String state;

    private Location location;

}
