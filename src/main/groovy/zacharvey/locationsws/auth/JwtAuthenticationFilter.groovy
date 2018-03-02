package zacharvey.locationsws.auth

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import zacharvey.locationsws.config.LocationsWsProperties
import zacharvey.locationsws.domain.entities.Account
import zacharvey.locationsws.domain.models.SignInRequest
import zacharvey.locationsws.repositories.AccountRepository
import zacharvey.locationsws.resources.ErrorResponse
import zacharvey.locationsws.services.AccountService

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements SecurityConstants {
    AccountRepository accountRepository
    AccountService accountService
    LocationsWsProperties locationsWsProperties
    AuthenticationManager authenticationManager
    ObjectMapper objectMapper
    UsernamePasswordAuthenticationTokenFactory tokenFactory

    JwtAuthenticationFilter(AccountRepository accountRepository, AccountService accountService,
                            LocationsWsProperties locationsWsProperties, AuthenticationManager authenticationManager,
                            ObjectMapper objectMapper, UsernamePasswordAuthenticationTokenFactory tokenFactory) {
        super()

        this.accountRepository = accountRepository
        this.accountService = accountService
        this.locationsWsProperties = locationsWsProperties
        this.authenticationManager = authenticationManager
        this.objectMapper = objectMapper
        this.tokenFactory = tokenFactory

        setFilterProcessesUrl('/v1/auth/signIn')
    }

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Authentication authentication

        log.trace("Received an auth attempt...")

        try {
            SignInRequest signInRequest = objectMapper.readValue(request.inputStream, SignInRequest)

            Account account = accountRepository.findByEmail(signInRequest.email)
            if(!account) {
                throw new UsernameNotFoundException(signInRequest.email)
            } else {
                authentication = authenticationManager.authenticate(tokenFactory.createToken(account.email, signInRequest.password))
            }
        } catch(Throwable throwable) {
            String receipt = UUID.randomUUID().toString()
            String reason = 'Authentication failed.'
            int errorStatus = HttpStatus.UNAUTHORIZED.value()

            ErrorResponse errorResponse = new ErrorResponse(receipt, reason)
            log.error("Authentication-related exception (${receipt}): ${throwable.class}: ${ExceptionUtils.getStackTrace(throwable)}")


            response.setStatus(errorStatus)
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse))
        }

        log.trace("${this.class}#attemptAuthentication: Authentication seems to have passed!")
        authentication
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.trace("${this.class}#successfulAuthentication: Auth passed, generating a token.")

        User user = authResult.principal as User

        String token = Jwts.builder()
            .setSubject(user.username)
            .setExpiration(new Date(System.currentTimeMillis() + locationsWsProperties.jwtInfo.expiry))
            .signWith(SignatureAlgorithm.HS512, locationsWsProperties.jwtInfo.secret.bytes)
            .compact()

        response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, AUTH_HEADER_NAME)
        response.addHeader(AUTH_HEADER_NAME, "${BEARER_TOKEN_NAME} ${token}")
    }
}
