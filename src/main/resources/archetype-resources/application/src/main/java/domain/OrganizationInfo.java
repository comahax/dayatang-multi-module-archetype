#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.ValueObject;

/**
 * 单位信息
 * 
 */
@Embeddable
public class OrganizationInfo implements ValueObject {

	private static final long serialVersionUID = -4257681514577652756L;

	@ManyToOne(targetEntity = Organization.class)
	@JoinColumn(name = "organization_id")
	private Organization organization;

	/**
	 * 单位联系人
	 */
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	public OrganizationInfo() {
	}

	public OrganizationInfo(Organization organization, Person person) {
		this.organization = organization;
		this.person = person;
	}

	public boolean equals(final Object other) {
		if (!(other instanceof OrganizationInfo)) {
			return false;
		}
		OrganizationInfo that = (OrganizationInfo) other;
		return new EqualsBuilder().append(this.getOrganization(), that.getOrganization())
				.append(this.getPerson(), that.getPerson()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getOrganization()).append(getPerson()).toHashCode();
	}

	public String toString() {
		return getOrganization() + "  " + getPerson();
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
