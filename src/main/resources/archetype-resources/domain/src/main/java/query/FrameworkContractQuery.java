#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.FrameworkContract;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import com.dayatang.utils.Assert;

public class FrameworkContractQuery extends BaseQuery<FrameworkContract> {

	private FrameworkContractQuery() {
		super(QuerySettings.create(FrameworkContract.class));
	}

	public static FrameworkContractQuery create() {
		return new FrameworkContractQuery();
	}

	public static FrameworkContractQuery grantedScopeIn(InternalOrganization scope) {
		Assert.notNull(scope);
		FrameworkContractQuery query = new FrameworkContractQuery();
		query.querySettings.and(new GeCriterion("grantedScope.leftValue", scope.getLeftValue()), new LeCriterion(
				"grantedScope.rightValue", scope.getRightValue()));
		return query;
	}

	public FrameworkContractQuery project(Project project) {
		querySettings.eq("project", project);
		return this;
	}

    public FrameworkContractQuery nameContainsText(String name) {
        querySettings.containsText("contractName", name);
        return this;
    }

    public FrameworkContractQuery serialNumber(String number) {
        querySettings.containsText("serialNumber", number);
        return this;
    }
}
