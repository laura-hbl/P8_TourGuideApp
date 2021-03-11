package tourGuide.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.VisitedLocationDTO;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "gps-microservice", url = "${proxy.gps}")
public interface MicroserviceGpsProxy {

    @GetMapping("/userLocation/{userId}")
    VisitedLocationDTO getUserLocation(@PathVariable("userId") final UUID userId);

    @GetMapping("/attractions")
    List<AttractionDTO> getAttractions();
}
