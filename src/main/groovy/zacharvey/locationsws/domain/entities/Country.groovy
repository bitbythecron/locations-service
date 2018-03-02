package zacharvey.locationsws.domain.entities

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import zacharvey.locationsws.serialization.CountrySerializer

import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Canonical
@TupleConstructor(includeSuperProperties = true)
@ToString(includeSuperProperties = true)
@Entity
@Table(name = 'countries')
@AttributeOverrides([
        @AttributeOverride(name = "id", column=@Column(name="country_id")),
        @AttributeOverride(name = "name", column=@Column(name="country_name")),
        @AttributeOverride(name = "label", column=@Column(name="country_label")),
        @AttributeOverride(name = "description", column=@Column(name="country_description")),
])
@JsonSerialize(using = CountrySerializer)
class Country extends BaseLookup {
    @Column(name = 'country_code')
    @NotEmpty
    String code
}
