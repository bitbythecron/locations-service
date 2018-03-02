package zacharvey.locationsws.domain.entities

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import zacharvey.locationsws.serialization.CitySerializer

import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Canonical
@TupleConstructor(includeSuperProperties = true)
@ToString(includeSuperProperties = true)
@Entity
@Table(name = 'cities')
@AttributeOverrides([
        @AttributeOverride(name = 'id', column=@Column(name='city_id')),
        @AttributeOverride(name = 'name', column=@Column(name='city_name')),
        @AttributeOverride(name = 'label', column=@Column(name='city_label')),
        @AttributeOverride(name = 'description', column=@Column(name='city_description'))
])
@JsonSerialize(using = CitySerializer)
class City extends BaseLookup {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = 'province_id', referencedColumnName = 'province_id')
    @NotNull
    @Valid
    Province province
}
