package zacharvey.locationsws.services.impl

import groovy.transform.Canonical
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import zacharvey.locationsws.domain.entities.Account
import zacharvey.locationsws.repositories.AccountRepository
import zacharvey.locationsws.services.AccountService

@Canonical
class DefaultAccountService implements AccountService {
    AccountRepository accountRepository
    BCryptPasswordEncoder bCryptPasswordEncoder

    @Override
    void createAccount(String email, String password) {
        int newId = accountRepository.accountMap.size()++

        Account newAccount = new Account(newId, email, bCryptPasswordEncoder.encode(password))

        accountRepository.accountMap.put(newAccount.email, newAccount)
    }
}
