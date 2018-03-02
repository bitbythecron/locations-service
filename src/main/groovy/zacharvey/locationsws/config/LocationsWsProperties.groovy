package zacharvey.locationsws.config

import groovy.transform.Canonical
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Config properties holder. Either injected from the command-line or set to default values from
 * inside <code>application.yml</code>.
 */
@Component
@ConfigurationProperties('locations')
@Canonical
class LocationsWsProperties {
    JwtInfo jwtInfo
}
