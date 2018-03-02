package zacharvey.locationsws.auditing

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.Canonical
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * Central auditing component for the app. Listens for {@link AuditApplicationEvent AuditApplicationEvents} and
 * handles them.
 */
@Slf4j
@Canonical
@TupleConstructor(includeSuperProperties = true)
@Component
class Auditor {
    @Autowired
    ObjectMapper objectMapper

    @EventListener
    void handleAuditEvent(AuditApplicationEvent auditApplicationEvent) {
        log.info(objectMapper.writer().writeValueAsString(auditApplicationEvent.auditEvent))
    }
}
