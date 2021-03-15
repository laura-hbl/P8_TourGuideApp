package tourGuide.unit.util;

import javax.money.Monetary;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.Provider;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.UserPreferences;
import tourGuide.util.ModelConverter;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    @Tag("Valid")
    @DisplayName("Given an UserPreferencesDTO, when ToUserPreferences, then result should match expected UserPreferences")
    public void givenAnUserPreferencesDTO_whenToUserPreferences_thenReturnExpectedUserPreferences() {
        UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO(10, 100,
                300, 3, 2, 1, 1);

        UserPreferences userPreferences = new UserPreferences(10,
                Money.of(100, Monetary.getCurrency("USD")),
                Money.of(300, Monetary.getCurrency("USD")), 3,
                2, 1, 1);

        UserPreferences result = modelConverter.toUserPreferences(userPreferencesDTO);

        assertThat(result).isEqualToComparingFieldByField(userPreferences);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null UserPreferencesDTO, then toUserPreferences should raise an NullPointerException")
    public void givenANullUserPreferencesDTO_whenToUserPreferences_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> modelConverter.toUserPreferences(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given a VisitedLocationDTO, when ToVisitedLocation, then result should match expected VisitedLocation")
    public void givenAVisitedLocationDTO_whenToVisitedLocation_thenReturnExpectedVisitedLocation() {
        UUID userID = UUID.randomUUID();
        VisitedLocationDTO visitedLocationDTO = new VisitedLocationDTO(userID, new Location(-160.326003,
                -73.869629), new Date());

        VisitedLocation visitedLocation = new VisitedLocation(userID, new Location(-160.326003,
                -73.869629), new Date());

        VisitedLocation result = modelConverter.toVisitedLocation(visitedLocationDTO);

        assertThat(result).isEqualToComparingFieldByField(visitedLocation);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null VisitedLocationDTO, then toVisitedLocation should raise an NullPointerException")
    public void givenANullVisitedLocationDTO_whenToVisitedLocation_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> modelConverter.toVisitedLocation(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given an AttractionDTO, when ToAttraction, then result should match expected Attraction")
    public void givenAnAttractionDTO_whenToAttraction_thenReturnExpectedAttraction() {
        UUID attractionID = UUID.randomUUID();

        AttractionDTO attractionDTO = new AttractionDTO(attractionID, "Disneyland" , "Anaheim" ,
                "CA" , new Location(-117.922008, 33.817595));

        Attraction attraction = new Attraction(attractionID, "Disneyland" , "Anaheim" ,
                "CA" , new Location(-117.922008, 33.817595));

        Attraction result = modelConverter.toAttraction(attractionDTO);

        assertThat(result).isEqualToComparingFieldByField(attraction);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null AttractionDTO, then toAttraction should raise an NullPointerException")
    public void givenANullAttractionDTO_whenToAttraction_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> modelConverter.toAttraction(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given a ProviderDTO, when ToAProvider, then result should match expected Provider")
    public void givenAProviderDTO_whenToProvider_thenReturnExpectedProvider() {
        UUID tripID = UUID.randomUUID();
        Provider attraction = new Provider("Provider", 150 , tripID);

        Provider result = modelConverter.toProvider(new ProviderDTO("Provider" , 150 , tripID));

        assertThat(result).isEqualToComparingFieldByField(attraction);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null ProviderDTO, then toProvider should raise an NullPointerException")
    public void givenANullProviderDTO_whenToProvider_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> modelConverter.toProvider(null));
    }
}
