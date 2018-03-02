package zacharvey.locationsws.resources

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zacharvey.locationsws.domain.entities.City
import zacharvey.locationsws.domain.entities.Country
import zacharvey.locationsws.domain.entities.Province
import zacharvey.locationsws.services.LocationService

@Slf4j
@RestController
@RequestMapping('/v1/data/locations')
class LocationsResource {
    @Autowired
    LocationService locationService

    @GetMapping('/countries')
    ResponseEntity<Set<Country>> getAllCountries() {
        new ResponseEntity<Set<Country>>(locationService.getAllCountries(), HttpStatus.OK)
    }

    @GetMapping('/provinces')
    ResponseEntity<Set<Province>> getProvincesByCountryCode(@RequestParam(value = 'countryCode') String countryCode) {
        Objects.requireNonNull(countryCode)

        new ResponseEntity<Set<Province>>(locationService.getProvincesByCountryCode(countryCode), HttpStatus.OK)
    }

    @GetMapping('/cities')
    ResponseEntity<Set<City>> getCitiesByProvinceId(@RequestParam(value = 'provinceId') String provinceId) {
        Objects.requireNonNull(provinceId)

        new ResponseEntity<Set<City>>(locationService.getCitiesByProvinceId(Long.valueOf(provinceId)), HttpStatus.OK)
    }
}
