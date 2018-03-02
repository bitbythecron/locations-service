package zacharvey.locationsws.resources

import com.codahale.metrics.MetricRegistry
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zacharvey.locationsws.auth.SecurityConstants
import zacharvey.locationsws.domain.models.RegistrationRequest
import zacharvey.locationsws.repositories.AccountRepository
import zacharvey.locationsws.services.AccountService
import zacharvey.locationsws.validators.EmailValidator

import javax.validation.Valid
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * RESTful controller/resource handler for the "<i>auth</i>" endpoints, typically used for new user sign up,
 * authentication & sign in/JWT generation, sign out and other authn/authz-related tasks.
 */
@Slf4j
@RestController
@RequestMapping('/v1/auth')
class AuthResource implements SecurityConstants {
    @Autowired
    EmailValidator emailValidator

    @Autowired
    MetricRegistry metricRegistry

    @Autowired
    AccountService accountService

    @Autowired
    AccountRepository accountRepository

    @PutMapping('/signUp')
    void signUp(@RequestBody @Valid RegistrationRequest registrationRequest) {
        metricRegistry.meter('metrics.meters.resources.auth.signup').mark()

        if(!emailValidator.isValidEmail(registrationRequest.email) || accountRepository.findByEmail(registrationRequest.email)) {
            throw new IllegalArgumentException('Email was invalid!')
        }
        // Minimum 6 characters, at least one letter, one number and one special character
        if(!registrationRequest.password) {
            throw new IllegalArgumentException('Password is required!')
        }
        Pattern pwdPattern = Pattern.compile('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,}$')
        Matcher pwdMatcher = pwdPattern.matcher(registrationRequest.password)

        if(!pwdMatcher.matches()) {
            throw new IllegalArgumentException('Password does not meet minimum security criteria!')
        }

        accountService.createAccount(registrationRequest.email, registrationRequest.password)

        ResponseEntity.ok().build()
    }
}
