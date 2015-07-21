#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.Organization;

public class OrganizationQuery extends BaseQuery<Organization> {

	public OrganizationQuery() {
		super(QuerySettings.create(Organization.class));
	}

	public static OrganizationQuery create() {
		return new OrganizationQuery();
	}

	public OrganizationQuery organizationId(long id) {
		querySettings.eq("id", id);
		return this;
	}

	public OrganizationQuery nameEq(String organizationName) {
		querySettings.eq("name", organizationName);
		return this;
	}

	public OrganizationQuery nameLike(String organizationNamePiece) {
		querySettings.containsText("name", organizationNamePiece);
		return this;
	}

}
