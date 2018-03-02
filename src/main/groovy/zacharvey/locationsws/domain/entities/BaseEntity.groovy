package zacharvey.locationsws.domain.entities

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * Abstract base class for all persisted entities. All entities have an <b>id</b> which is NEVER to be exposed
 * externally and is only used internally for lookups. When we <i>do</i> have to expose it externally we use its
 * accompanying <b>refId</b> instead.
 *
 * <p>So <code>refIds</code> are allowed to be used externally and will never change once the record is created and
 * for reference/non-volatile data should eveb be the same across all environments. But <code>ids</code> will be
 * different in every environment, will change if we migrate to different databases, etc.
 */
@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
}
