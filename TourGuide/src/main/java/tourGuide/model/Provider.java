package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Provider {

    private String name;

    private double price;

    private UUID tripId;

}
