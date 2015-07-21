#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

@Entity
@Table(name = "persons")
public class Person extends AbstractCommonEntity implements Comparable<Person> {

	private static final long serialVersionUID = 6258174888274855077L;

	private String name;

	/**
	 * 性别
	 */
	private Gender gender;

	private String title;

	private String mobile;

	private String tel;

	private String email;

	private String qq;

	@ManyToOne
	@JoinColumn(name = "organization_id")
	private Organization organization;

	public Person(String name) {
		this.name = name;
	}

	public Person() {
	}

	public boolean isMan() {
		return Gender.MALE.equals(getGender());
	}

	public boolean isWoman() {
		return Gender.FEMALE.equals(getGender());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public static Person get(Long id) {
		return AbstractEntity.get(Person.class, id);
	}

	public static List<Person> findByOrganizationCategory(InternalOrganizationCategory category) {
		return getRepository().find(QuerySettings.create(Person.class).eq("organization.category", category));
	}

	public static List<Person> findByOrganization(Organization organization) {
		return getRepository().find(QuerySettings.create(Person.class).eq("organization", organization));
	}
	
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Person))
			return false;
		Person castOther = (Person) other;
		return new EqualsBuilder().append(name, castOther.name).append(mobile, castOther.mobile).append(email, castOther.email)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(name).append(mobile).append(email).toHashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	public int compareTo(Person o) {
		return new CompareToBuilder().append(this.getId(), o.getId()).toComparison();
	}

}
