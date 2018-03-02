package zacharvey.locationsws.repositories

import org.springframework.data.repository.CrudRepository
import zacharvey.locationsws.domain.entities.Country

interface CountryRepository extends CrudRepository<Country,Long> {
}
