package tourGuide.unit.util;

import javax.money.Monetary;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tourGuide.dto.LocationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.UserRewardDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.UserPreferences;
import tourGuide.model.user.UserReward;
import tourGuide.util.DTOConverter;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @Tag("Valid")
    @DisplayName("Given a UserPreferences, when ToUserPreferencesDTO, then result should match expected UserPreferencesDTO")
    public void givenAnUserPreferences_whenToUserPreferencesDTO_thenReturnExpectedUserPreferencesDTO() {
        UserPreferencesDTO userPreferencesDTO = new UserPreferencesDTO(10, 100,
                300, 3, 2, 1, 1);

        UserPreferences userPreferences = new UserPreferences(10,
                Money.of(100, Monetary.getCurrency("USD")),
                Money.of(300, Monetary.getCurrency("USD")), 3,
                2, 1, 1);

        UserPreferencesDTO result = dtoConverter.toUserPreferencesDTO(userPreferences);

        assertThat(result).isEqualToComparingFieldByField(userPreferencesDTO);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null UserPreferences, then toUserPreferencesDTO should raise an NullPointerException")
    public void givenANullUserPreferences_whenToUserPreferencesDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toUserPreferencesDTO(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given an UserReward, when ToUserRewardDTO, then result should match expected UserRewardDTO")
    public void givenAnUserReward_whenToUserRewardDTO_thenReturnExpectedUserRewardDTO() {
        VisitedLocation visitedLocation = new VisitedLocation(UUID.randomUUID(), new Location(-160.326003,
                -73.869629), new Date());
        Attraction attraction = new Attraction(UUID.randomUUID(), "Disneyland" , "Anaheim" ,
                "CA" , new Location(-117.922008, 33.817595));
        UserRewardDTO userRewardDTO = new UserRewardDTO(visitedLocation, attraction, 300);

        UserRewardDTO result = dtoConverter.toUserRewardDTO(new UserReward(visitedLocation, attraction, 300));

        assertThat(result).isEqualToComparingFieldByField(userRewardDTO);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given an null UserReward, then toUserRewardDTO should raise an NullPointerException")
    public void givenANullUserReward_whenToUserRewardDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toUserRewardDTO(null));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Given a Location, when ToLocationDTO, then result should match expected LocationDTO")
    public void givenALocation_whenToLocationDTO_thenReturnExpectedLocationDTO() {
        LocationDTO locationDTO = new LocationDTO(-117.922008, 33.817595);

        LocationDTO result = dtoConverter.toLocationDTO(new Location(-117.922008, 33.817595));

        assertThat(result).isEqualToComparingFieldByField(locationDTO);
    }

    @Test
    @Tag("Exception")
    @DisplayName("Given a null Location, then toLocationDTO should raise an NullPointerException")
    public void givenANullLocation_whenToLocationDTO_thenNullPointerExceptionIsThrown() {
        assertThatNullPointerException().isThrownBy(() -> dtoConverter.toLocationDTO(null));
    }
}

