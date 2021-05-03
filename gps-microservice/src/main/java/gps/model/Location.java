package gps.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Location {

    /**
     * The location longitude.
     */
    private double longitude;

    /**
     * The location latitude.
     */
    private double latitude;
}
