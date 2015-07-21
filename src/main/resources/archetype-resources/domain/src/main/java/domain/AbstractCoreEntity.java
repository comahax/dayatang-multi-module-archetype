#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dayatang.domain.AbstractEntity;
import ${package}.domain.Person;

@MappedSuperclass
public abstract class AbstractCoreEntity extends AbstractEntity {

	private static final long serialVersionUID = -5685905134071831221L;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private Person creator;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date created;

	@Column(name = "last_updated")
	@Temporal(TemporalType.DATE)
	private Date lastUpdated;

	@ManyToOne
	@JoinColumn(name = "last_updator")
	private Person lastUpdator;

	private boolean disabled = false;

	public void setCreatorAndCreated(Date created, Person creator) {
		this.created = created;
		this.creator = creator;
	}

	public void disabled() {
		this.disabled = true;
	}

	public void abled() {
		this.disabled = false;
	}

	public boolean isAbled() {
		return !isDisabled();
	}

	public void lastUpdate(Person lastUpdator, Date lastUpdated) {
		this.lastUpdated = lastUpdated;
		this.lastUpdator = lastUpdator;
	}

	public static <T extends AbstractCoreEntity> Collection<T> setCreatorAndCreated(Collection<T> coreEntities, Person creator,
			Date created) {
		if (null == coreEntities || coreEntities.isEmpty()) {
			return null;
		}
		for (AbstractCoreEntity each : coreEntities) {
			if (null == each) {
				continue;
			}
			each.created = created;
			each.creator = creator;
		}
		return coreEntities;
	}

	public void log(Person creator, Date created) {
		this.creator = creator;
		this.created = created;
	}

	public void logLastUpdated() {
		this.lastUpdated = new Date();
	}

	public Person getCreator() {
		return creator;
	}

	public void setCreator(Person creator) {
		this.creator = creator;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Person getLastUpdator() {
		return lastUpdator;
	}

	public void setLastUpdator(Person lastUpdator) {
		this.lastUpdator = lastUpdator;
	}

}
