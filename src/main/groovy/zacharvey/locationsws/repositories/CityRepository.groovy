package zacharvey.locationsws.repositories

import org.springframework.data.repository.CrudRepository
import zacharvey.locationsws.domain.entities.City

interface CityRepository extends CrudRepository<City,Long> {
}
