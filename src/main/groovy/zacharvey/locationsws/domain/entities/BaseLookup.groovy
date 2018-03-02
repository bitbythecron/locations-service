package zacharvey.locationsws.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotNull

/**
 * Many tables will be "<i>lookups</i>" or "reference tables", that is: they store non-volatile data that doesn't
 * change at app runtime. All of these lookup tables will all have (at least) a public-facing, pretty-print
 * <code>name</code> field, an internal SCREAMING_UPPER_CASE <code>label</code> to be used for intenal queries,
 * as well as a simple <code>description</code> of what status/value the record represents.
 */
@TupleConstructor(includeSuperProperties = true)
@ToString(includeSuperProperties = true)
@MappedSuperclass
abstract class BaseLookup extends BaseEntity {
    @JsonIgnore
    @NotNull
    String name

    @NotNull
    String label

    @JsonIgnore
    @NotNull
    String description
}
