package zacharvey.locationsws.services.impl

import zacharvey.locationsws.domain.entities.City
import zacharvey.locationsws.domain.entities.Country
import zacharvey.locationsws.domain.entities.Province
import zacharvey.locationsws.services.LocationService

class DefaultLocationService implements LocationService {
    static Set<Country> allCountries
    static Set<Province> allProvinces
    static Set<City> allCities

    static {
        Country usa = new Country(1, 'United States', 'UNITED_STATES',
            'United States of America', 'US')
        Country can = new Country(2, 'Canada', 'CANADA',
                'Canada', 'CA')
        allCountries = []
        allCountries << usa
        allCountries << can

        Province ny = new Province(1, 'New York', 'NEW_YORK', 'NYS', 'NY', usa)
        Province vt = new Province(2, 'Vermont', 'VERMONT', 'VT', 'VT', usa)
        Province on = new Province(3, 'Ontario', 'ONTARIO', 'ON', 'ON', can)
        Province qb = new Province(4, 'Quebec', 'QUEBEC', 'QB', 'QB', can)
        allProvinces = []
        allProvinces << ny
        allProvinces << vt
        allProvinces << on
        allProvinces << qb

        allCities = []
        allCities << new City(1, 'New York City', 'NEW_YORK_CITY', 'NYC', ny)
        allCities << new City(2, 'Brattleboro', 'BRATTLEBORO', 'Brattleboro', vt)
        allCities << new City(3, 'Ottawa', 'OTTAWA', 'Ottawa', on)
        allCities << new City(4, 'Montreal', 'MONTREAL', 'Montreal', qb)
    }

    @Override
    Set<Country> getAllCountries() {
        allCountries
    }

    @Override
    Set<Province> getProvincesByCountryCode(String countryCode) {
        Set<Province> provinces = []
        allProvinces.each { province ->
            if(province.country.code.equalsIgnoreCase(countryCode)) {
                provinces << province
            }
        }

        provinces
    }

    @Override
    Set<City> getCitiesByProvinceId(Long provinceId) {
        Set<City> cities = []
        allCities.each { city ->
            if(city.province.id.equals(provinceId)) {
                cities << city
            }
        }

        cities
    }
}
