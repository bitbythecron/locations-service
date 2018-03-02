package zacharvey.locationsws.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import zacharvey.locationsws.domain.entities.Account

interface AccountRepository extends CrudRepository<Account,Long> {
    @Query("FROM Account WHERE email = :email")
    Account findByEmail(@Param(value = 'email') String email)
}
