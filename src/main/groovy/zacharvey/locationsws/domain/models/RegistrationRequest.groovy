package zacharvey.locationsws.domain.models

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * Simple DTO sent when an unverified prospective user is signing up for the app for the first time.
 */
@Canonical
@TupleConstructor(includeSuperProperties = true)
@ToString(includeSuperProperties = true)
class RegistrationRequest {
    @JsonProperty(value = 'email')
    @NotEmpty
    String email

    @JsonProperty(value = 'password')
    @Size(min = 6)
    String password
}
