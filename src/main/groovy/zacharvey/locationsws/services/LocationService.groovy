package zacharvey.locationsws.services

import zacharvey.locationsws.domain.entities.City
import zacharvey.locationsws.domain.entities.Country
import zacharvey.locationsws.domain.entities.Province

interface LocationService {
    Set<Country> getAllCountries()
    Set<Province> getProvincesByCountryCode(String countryCode)
    Set<City> getCitiesByProvinceId(Long provinceId)
}
