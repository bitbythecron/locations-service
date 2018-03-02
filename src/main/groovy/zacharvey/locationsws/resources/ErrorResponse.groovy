package zacharvey.locationsws.resources

import groovy.transform.Canonical
import groovy.transform.TupleConstructor

/**
 * Generic error response sent back to the requesting client anytime a request produces an exception/error message
 * that can be caught & handled on the server.
 */
@Canonical
@TupleConstructor(includeSuperProperties = true)
class ErrorResponse {
    /**
     * 36-char UUID that can be used for tracking specific exceptions/errors
     */
    String receipt

    /**
     * Tag with some details explaining what went wrong with the request
     */
    String reason
}