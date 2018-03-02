package zacharvey.locationsws.auth

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import zacharvey.locationsws.domain.entities.Account
import zacharvey.locationsws.repositories.AccountRepository

/**
 * {@link UserDetailsService} impl that fetches {@link Account Accounts} from the database and searches for one
 * that matches the provided username (which is taken off the JWT subject).
 */
@Slf4j
@Component
class PersistedUserDetailsService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
        if(!account) {
            throw new UsernameNotFoundException(email)
        }

        new User(account.email, account.password, [])
    }
}
