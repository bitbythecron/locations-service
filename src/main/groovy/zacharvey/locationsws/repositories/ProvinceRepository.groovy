package zacharvey.locationsws.repositories

import org.springframework.data.repository.CrudRepository
import zacharvey.locationsws.domain.entities.Province

interface ProvinceRepository extends CrudRepository<Province,Long> {
}
