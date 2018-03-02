package zacharvey.locationsws.auth

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import zacharvey.locationsws.config.LocationsWsProperties
import zacharvey.locationsws.repositories.AccountRepository
import zacharvey.locationsws.services.AccountService

@EnableWebSecurity
@Slf4j
class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    AccountRepository accountRepository

    @Autowired
    AccountService accountService

    @Autowired
    LocationsWsProperties locationsWsProperties

    @Autowired
    UserDetailsService userDetailsService

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    UsernamePasswordAuthenticationTokenFactory tokenFactory

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
            // Cherry pick unauthenticated URLS here (e.g. URLs that bypass auth)
            .antMatchers(HttpMethod.OPTIONS, "/v1/**").permitAll()
            .antMatchers(HttpMethod.PUT, '/v1/auth/signUp').permitAll()

            // All other URLs are authenticated.
            .anyRequest().authenticated()
            .and()
            .addFilter(new JwtAuthenticationFilter(accountRepository, accountService, locationsWsProperties,
                authenticationManager(), objectMapper, tokenFactory))
            .addFilter(new JwtAuthorizationFilter(locationsWsProperties, tokenFactory, objectMapper, authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration()
        corsConfiguration.setAllowedOrigins(Arrays.asList('*'))
        corsConfiguration.setAllowedMethods(Arrays.asList('GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'))
        corsConfiguration.setAllowedHeaders(Arrays.asList('Authorization', 'Content-Type', 'X-Auth-Token'))
        corsConfiguration.setExposedHeaders(Arrays.asList('X-Auth-Token'))

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration('/**', corsConfiguration)

        source
    }
}
