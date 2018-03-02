package zacharvey.locationsws.auth

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

/**
 * Factory for {@link UsernamePasswordAuthenticationToken} instances.
 */
class UsernamePasswordAuthenticationTokenFactory {
    UsernamePasswordAuthenticationToken createToken(String email, String passwordHash) {
        new UsernamePasswordAuthenticationToken(email, passwordHash, [])
    }
}
