package zacharvey.locationsws.listeners

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.ScheduledReporter
import com.codahale.metrics.Timer
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.Canonical
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import zacharvey.locationsws.config.LocationsWsProperties

import java.util.concurrent.TimeUnit

/**
 * Runs at app startup, performs basic initialization work.
 */
@Component
@Slf4j
@Canonical
@TupleConstructor(includeSuperProperties = true)
class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    ScheduledReporter metricReporter

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    LocationsWsProperties locationsWsProperties

    @Autowired
    MetricRegistry metricRegistry

    @Override
    void onApplicationEvent(ContextRefreshedEvent event) {
        Timer.Context timerCtx = metricRegistry.timer('metrics.timer.startuplistener').time()
        log.info("${StartUpListener} is running...")

        metricReporter.start(20, TimeUnit.SECONDS)

        // Init here...

        timerCtx.stop()
    }
}
