package zacharvey.locationsws.auth

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import io.jsonwebtoken.Jwts
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import zacharvey.locationsws.config.LocationsWsProperties
import zacharvey.locationsws.resources.ErrorResponse

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
class JwtAuthorizationFilter extends BasicAuthenticationFilter implements SecurityConstants {
    LocationsWsProperties locationsWsProperties
    UsernamePasswordAuthenticationTokenFactory tokenFactory
    ObjectMapper objectMapper

    JwtAuthorizationFilter(LocationsWsProperties locationsWsProperties1,
                           UsernamePasswordAuthenticationTokenFactory tokenFactory, ObjectMapper objectMapper,
                           AuthenticationManager authenticationManager) {
        super(authenticationManager)

        this.locationsWsProperties = locationsWsProperties
        this.tokenFactory = tokenFactory
        this.objectMapper = objectMapper
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String jwtToken = request.getHeader(AUTH_HEADER_NAME)

            log.trace("${this.class}#doFilterInternal: received JWT of ${jwtToken}")

            if(!jwtToken || !jwtToken.startsWith(BEARER_TOKEN_NAME)) {
                log.trace("${this.class}#doFilterInternal: This JWT is null or doesn't start with \"Bearer\".")
                chain.doFilter(request, response)
                return
            }

            UsernamePasswordAuthenticationToken authenticationToken = doAuthentication(jwtToken)
            SecurityContextHolder.getContext().setAuthentication(authenticationToken)
            log.trace("${this.class}#doFilterInternal: This JWT seemed valid and no exceptions were thrown.")
            chain.doFilter(request, response)
        } catch(RuntimeException rtEx) {
            String receipt = UUID.randomUUID().toString()
            log.error("Authorization-related exception (${receipt}): ${rtEx.class}: ${ExceptionUtils.getStackTrace(rtEx)}")
            ErrorResponse errorResponse = new ErrorResponse(receipt, 'Authentication failed.')

            response.setStatus(HttpStatus.UNAUTHORIZED.value())
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse))
        }
    }

    private UsernamePasswordAuthenticationToken doAuthentication(String jwtToken) {
        UsernamePasswordAuthenticationToken token
        if(jwtToken) {
            String principal = Jwts.parser()
                .setSigningKey(locationsWsProperties.jwtInfo.secret.bytes)
                .parseClaimsJws(jwtToken.replace(BEARER_TOKEN_NAME, ''))
                .getBody()
                .getSubject()

            if(principal) {
                token = tokenFactory.createToken(principal, null)
                log.trace("${this.class}#doAuthentication: Principal \"${principal}\" has made a service request.")
            }
        }

        token
    }
}
