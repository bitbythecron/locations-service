package zacharvey.locationsws.domain.entities

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import zacharvey.locationsws.serialization.ProvinceSerializer

import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Canonical
@TupleConstructor(includeSuperProperties = true)
@ToString(includeSuperProperties = true)
@Entity
@Table(name = 'provinces')
@AttributeOverrides([
        @AttributeOverride(name = 'id', column=@Column(name='province_id')),
        @AttributeOverride(name = 'name', column=@Column(name='province_name')),
        @AttributeOverride(name = 'label', column=@Column(name='province_label')),
        @AttributeOverride(name = 'description', column=@Column(name='province_description'))
])
@JsonSerialize(using = ProvinceSerializer)
class Province extends BaseLookup {
    @Column(name = 'province_code')
    @NotEmpty
    String code

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = 'country_id', referencedColumnName = 'country_id')
    @NotNull
    @Valid
    Country country
}
