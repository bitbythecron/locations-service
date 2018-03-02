package zacharvey.locationsws.config

import groovy.transform.ToString
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * JWT/security-related configuration properties.
 */
@Component
@ConfigurationProperties('jwtInfo')
@ToString
class JwtInfo {
    /**
     * The JWT secret that all tokens will be signed with.
     */
    String secret

    /**
     * JWT expiration time (in millis).
     */
    Long expiry
}
