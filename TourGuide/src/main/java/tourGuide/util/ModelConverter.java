package tourGuide.util;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.ProviderDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.dto.VisitedLocationDTO;
import tourGuide.model.Attraction;
import tourGuide.model.Provider;
import tourGuide.model.VisitedLocation;
import tourGuide.model.user.UserPreferences;

@Component
public class ModelConverter {

    public  VisitedLocation toVisitedLocation(final VisitedLocationDTO visitedLocationDTO) {

        return new VisitedLocation(visitedLocationDTO.getUserId(), visitedLocationDTO.getLocation(),
                visitedLocationDTO.getTimeVisited());
    }

    public Provider toProvider(final ProviderDTO providerDTO) {

        return new Provider(providerDTO.getName(), providerDTO.getPrice(), providerDTO.getTripId());
    }

    public Attraction toAttraction(AttractionDTO attractionDTO) {

        return new Attraction(attractionDTO.getAttractionId(), attractionDTO.getAttractionName(),
                attractionDTO.getCity(), attractionDTO.getState(), attractionDTO.getLocation());

    }

    public UserPreferences toUserPreferences(UserPreferencesDTO preferences) {

        return new UserPreferences(preferences.getAttractionProximity(), Money.of(
                preferences.getLowerPricePoint(), preferences.getCurrency()),  Money.of(preferences.getHighPricePoint(),
                preferences.getCurrency()), preferences.getTripDuration(), preferences.getTicketQuantity(),
                preferences.getNumberOfAdults(), preferences.getNumberOfChildren());
    }
}
