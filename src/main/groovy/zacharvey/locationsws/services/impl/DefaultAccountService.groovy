package zacharvey.locationsws.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import zacharvey.locationsws.domain.entities.Account
import zacharvey.locationsws.repositories.AccountRepository
import zacharvey.locationsws.services.AccountService

class DefaultAccountService implements AccountService {
    @Autowired
    AccountRepository accountRepository

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder

    @Override
    void createAccount(String email, String password) {
        Account newAccount = new Account(null, UUID.randomUUID().toString(), email,
                bCryptPasswordEncoder.encode(password))

        accountRepository.save(newAccount)
    }
}
