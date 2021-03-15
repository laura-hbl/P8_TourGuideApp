package gps.unit.util;

import gps.dto.AttractionDTO;
import gps.dto.VisitedLocationDTO;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import gps.util.DTOConverter;
import gpsUtil.location.Attraction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @Tag("Valid")
    @DisplayName("Given a VisitedLocation, when ToVisitedLocationDTO, then result should match expected VisitedLocationDTO")
    public void givenAVisitedLocationDTO_whenToVisitedLocation_thenReturnExpectedVisitedLocation() {
        UUID userID = UUID.randomUUID();
        Date date = new Date();
        VisitedLocation visitedLocation = new VisitedLocation(userID, new Location( -160.326003,
                -73.869629), date);

        VisitedLocationDTO result = dtoConverter.toVisitedLocationDTO(visitedLocation);

        assertThat(result.getUserId()).isEqualTo(userID);
        assertThat(result.getLocation().latitude).isEqualTo(-160.326003);
        assertThat(result.getLocation().longitude).isEqualTo(-73.869629);
        assertThat(result.getTimeVisited()).isEqualTo(date);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null VisitedLocation, then toVisitedLocationDTO should raise an NullPointerException")
    public void givenANullVisitedLocation_whenToVisitedLocationDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toVisitedLocationDTO(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given an Attraction, when ToAttractionDTO, then result should match expected AttractionDTO")
    public void givenAnAttraction_whenToAttractionDTO_thenReturnExpectedAttractionDTO() {

        Attraction attraction = new Attraction("name", "Disneyland" , "Anaheim",
                -117.922008, 33.817595);

        AttractionDTO result = dtoConverter.toAttractionDTO(attraction);

        assertThat(result.getAttractionName()).isEqualTo("name");
        assertThat(result.getCity()).isEqualTo("Disneyland");
        assertThat(result.getState()).isEqualTo("Anaheim");
        assertThat(result.getLocation().latitude).isEqualTo(-117.922008);
        assertThat(result.getLocation().longitude).isEqualTo(33.817595);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null Attraction, then toAttractionDTO should raise an NullPointerException")
    public void givenANullAttraction_whenToAttractionDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toAttractionDTO(null));
    }

}
