package zacharvey.locationsws.resources

import groovy.transform.Canonical
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Custom exception mapper used to map server-side exceptions to properly-formatted HTTP responses per the API
 * documentation standards.
 */
@Slf4j
@Canonical
@TupleConstructor(includeSuperProperties = true)
@ControllerAdvice
class LocationsWsExceptionMapper extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Throwable)
    ResponseEntity<ErrorResponse> uncaughtExceptionHandler(Throwable throwable) {
        String receipt = UUID.randomUUID().toString()
        log.error("Error receipt# ${receipt}: (${throwable.message})" + ExceptionUtils.getStackTrace(throwable))

        new ResponseEntity<ErrorResponse>(new ErrorResponse(receipt, throwable.message),
            HttpStatus.BAD_REQUEST)
    }
}
