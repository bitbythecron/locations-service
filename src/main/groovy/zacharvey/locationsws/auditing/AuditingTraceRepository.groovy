package zacharvey.locationsws.auditing

import groovy.transform.Canonical
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.audit.AuditEvent
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent
import org.springframework.boot.actuate.trace.Trace
import org.springframework.boot.actuate.trace.TraceRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

@Slf4j
@Canonical
@TupleConstructor(includeSuperProperties = true)
class AuditingTraceRepository implements TraceRepository {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher

    @Override
    List<Trace> findAll() {
        throw new UnsupportedOperationException("We don't expose trace information via /trace! See the log aggregator for all trace info.")
    }

    @Override
    void add(Map<String, Object> traceInfo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        String principal
        if(authentication in UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = authentication as UsernamePasswordAuthenticationToken
            principal = token.principal
        } else {
            principal = 'Anonymous'
        }

        AuditEvent traceRequestEvent = new AuditEvent(new Date(), principal, 'http.request.trace', traceInfo)
        AuditApplicationEvent traceRequestAppEvent = new AuditApplicationEvent(traceRequestEvent)

        applicationEventPublisher.publishEvent(traceRequestAppEvent)
    }
}
