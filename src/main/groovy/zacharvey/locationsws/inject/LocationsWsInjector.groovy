package zacharvey.locationsws.inject

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.MetricFilter
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.ScheduledReporter
import com.codahale.metrics.json.MetricsModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.trace.TraceRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import zacharvey.locationsws.auditing.AuditingTraceRepository
import zacharvey.locationsws.auth.UsernamePasswordAuthenticationTokenFactory
import zacharvey.locationsws.config.LocationsWsProperties
import zacharvey.locationsws.repositories.AccountRepository
import zacharvey.locationsws.services.AccountService
import zacharvey.locationsws.services.LocationService
import zacharvey.locationsws.services.impl.DefaultAccountService
import zacharvey.locationsws.services.impl.DefaultLocationService
import zacharvey.locationsws.validators.EmailValidator

import java.util.concurrent.TimeUnit

/**
 * DI container for the app.
 */
@Slf4j
@Configuration
class LocationsWsInjector {
    @Autowired
    LocationsWsProperties locationsWsProperties

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        new BCryptPasswordEncoder()
    }

    @Autowired
    void configureJackson(ObjectMapper objectMapper) {
        SimpleModule serializationModule = new SimpleModule()
//            .addSerializer(AccountSerializer, new AccountSerializer())
//            .addDeserializer(AccountDescriptorDeserializer, new AccountDescriptorDeserializer(accountDescriptorPersistor))

        objectMapper.registerModule(new MetricsModule(TimeUnit.SECONDS, TimeUnit.MILLISECONDS, false,
            MetricFilter.ALL))
        objectMapper.registerModule(serializationModule)
    }

    @Bean
    EmailValidator emailValidator() {
        new EmailValidator()
    }

    @Bean
    MetricRegistry metricRegistry() {
        new MetricRegistry()
    }

    @Bean
    ScheduledReporter metricReporter(MetricRegistry metricRegistry) {
        ConsoleReporter.forRegistry(metricRegistry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
    }

    @Bean
    TraceRepository traceRepository(ApplicationEventPublisher applicationEventPublisher) {
        new AuditingTraceRepository(applicationEventPublisher)
    }

    @Bean
    UsernamePasswordAuthenticationTokenFactory usernamePasswordAuthenticationTokenFactory() {
        new UsernamePasswordAuthenticationTokenFactory()
    }

    @Bean
    AccountService accountService(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        new DefaultAccountService(accountRepository, bCryptPasswordEncoder)
    }

    @Bean
    LocationService locationService() {
        new DefaultLocationService()
    }
}
