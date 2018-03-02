package zacharvey.locationsws.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import zacharvey.locationsws.domain.entities.Account

import java.util.concurrent.ConcurrentHashMap

class AccountRepository { //extends CrudRepository<Account,Long> {
//    @Query("FROM Account WHERE email = :email")
//    Account findByEmail(@Param(value = 'email') String email)

    Map<String,Account> accountMap = new ConcurrentHashMap<>()

    Account findByEmail(String email) {
        Objects.requireNonNull(email)

        Account account
        if(accountMap.containsKey(email)) {
            account = accountMap.get(email)
        }

        account
    }
}
