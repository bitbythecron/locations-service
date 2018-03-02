package zacharvey.locationsws.domain.models

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

import javax.validation.constraints.Size

@Canonical
public class SignInRequest {
    @JsonProperty(value = 'email')
    String email

    @JsonProperty(value = 'password')
    @Size(min = 6)
    String password
}
