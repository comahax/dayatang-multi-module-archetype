#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

@Entity
@Table(name = "organization_leaders")
public class OrganizationLeader extends AbstractEntity {

	private static final long serialVersionUID = 8856332951152384756L;

	@OneToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@OneToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;

	public OrganizationLeader() {

	}

	/**
	 * 找出指定机构对应的负责人
	 * 
	 * @return
	 */
	public Person findLeaderOf(Organization organization) {
		OrganizationLeader organizationLeader = getRepository().getSingleResult(
				QuerySettings.create(OrganizationLeader.class).eq("organization", this));
		return organizationLeader == null ? null : organizationLeader.getPerson();
	}

	public String findLeaderNameOf(Organization organization) {
		Person person = findLeaderOf(organization);
		return person == null ? null : person.getName();
	}

	public String getOrganizationLeader() {
		OrganizationLeader organizationLeader = getRepository().getSingleResult(
				QuerySettings.create(OrganizationLeader.class).eq("organization", this));
		if (null == organizationLeader) {
			return null;
		}
		Person person = organizationLeader.getPerson();
		if (person == null) {
			return null;
		}
		return person.getName();
	}

	public Person getPerson() {
		return person;
	}

	public Organization getOrganization() {
		return organization;
	}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof OrganizationLeader)) {
			return false;
		}
		OrganizationLeader that = (OrganizationLeader) other;
		return new EqualsBuilder().append(this.getPerson(), that.getPerson()).append(this.getOrganization(), that.getOrganization()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPerson()).append(getOrganization()).toHashCode();
	}

	@Override
	public String toString() {
		return getPerson().getName() + getOrganization().getName();
	}

}
