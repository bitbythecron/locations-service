package zacharvey.locationsws.domain.entities

import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * Represents a user in our domain model. Every user has an <i>account</i>.
 */
@Canonical
@TupleConstructor(includeSuperProperties = true)
@ToString(includeSuperProperties = true)
@Entity
@Table(name = 'accounts')
@AttributeOverrides([
        @AttributeOverride(name = 'id', column=@Column(name='account_id')),
        @AttributeOverride(name = 'refId', column=@Column(name='account_ref_id'))
])
class Account extends BaseEntity {
    @Column(name = 'account_email')
    @NotEmpty
    String email

    @Column(name = 'account_password')
    @Size(min = 6)
    String password
}
