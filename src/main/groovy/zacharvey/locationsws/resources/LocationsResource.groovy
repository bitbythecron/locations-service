package zacharvey.locationsws.resources

import groovy.util.logging.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zacharvey.locationsws.domain.entities.City
import zacharvey.locationsws.domain.entities.Country
import zacharvey.locationsws.domain.entities.Province

@Slf4j
@RestController
@RequestMapping('v1/data/locations')
class LocationsResource {
    @GetMapping('/countries')
    ResponseEntity<Set<Country>> getAllCountres() {

    }

    @GetMapping('/provinces')
    ResponseEntity<Set<Province>> getProvincesByCountryCode() {

    }

    @GetMapping('/cities')
    ResponseEntity<Set<City>> getCitiesByProvinceId() {

    }
}
