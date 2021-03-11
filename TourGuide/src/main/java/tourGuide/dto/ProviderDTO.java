package tourGuide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProviderDTO {

    private String name;

    private double price;

    private UUID tripId;

}
