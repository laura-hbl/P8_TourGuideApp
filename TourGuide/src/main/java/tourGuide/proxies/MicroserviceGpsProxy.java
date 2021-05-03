package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.VisitedLocationDTO;

import java.util.List;
import java.util.UUID;

/**
 * Permits to connection between TourGuide application and gps-microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "gps-microservice", url = "${proxy.gps}")
public interface MicroserviceGpsProxy {

    /**
     * Retrieves the location of an user.
     *
     * @param userId id of the user to be located
     * @return The user location converted to a VisitedLocationDTO object
     */
    @GetMapping("/userLocation/{userId}")
    VisitedLocationDTO getUserLocation(@PathVariable("userId") final UUID userId);

    /**
     * Retrieves the list of all attractions referenced by the GpsUtil.
     *
     * @return The attraction list
     */
    @GetMapping("/attractions")
    List<AttractionDTO> getAttractions();
}
