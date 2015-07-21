#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import org.apache.commons.lang3.StringUtils;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.AndCriterion;
import com.dayatang.domain.internal.EqCriterion;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.IsNullCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.FrameworkContract;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import ${package}.domain.SingleContract;
import com.dayatang.utils.Assert;
import com.dayatang.utils.DateUtils;

/**
 * 单项合同查询
 * 
 * @author zjzhai
 */
public class SingleContractQuery extends BaseQuery<SingleContract> {

	private SingleContractQuery() {
		super(QuerySettings.create(SingleContract.class));
	}

	/**
	 * 负责机构在这些机构内的合同
	 */
	public static SingleContractQuery grantedScopeIn(InternalOrganization scope) {
		Assert.notNull(scope);
		SingleContractQuery query = new SingleContractQuery();
		query.querySettings.and(new GeCriterion("grantedScope.leftValue", scope.getLeftValue()), new LeCriterion("grantedScope.rightValue", scope.getRightValue()));
		return query;
	}

	public SingleContractQuery signYear(String year) {
		if (StringUtils.isEmpty(year) || !StringUtils.isNumeric(year)) {
			return this;
		}
		querySettings.between("signDate", DateUtils.parseDate(year + "-01-01"), DateUtils.parseDate(year + "-12-31"));
		return this;
	}

	public SingleContractQuery nameLike(String name) {
		querySettings.containsText("contractName", name);
		return this;
	}

	public static SingleContractQuery create() {
		return new SingleContractQuery();
	}

	public SingleContractQuery project(Project project) {
		querySettings.eq("project", project);
		return this;
	}

	public SingleContractQuery frameworkContract(FrameworkContract frameworkContract) {
		querySettings.eq("frameworkContract", frameworkContract);
		return this;
	}

	public SingleContractQuery id(long singleContractId) {
		querySettings.eq("id", singleContractId);
		return this;
	}

	/**
	 * 找到不属于任何框架合同的(框架合同外的)所有单项合同
	 * 
	 * @return
	 */
	public SingleContractQuery allSingleContractOutOfFramework(FrameworkContract frameworkContract) {
		querySettings.or(new IsNullCriterion("project"),
				new AndCriterion(new IsNullCriterion("frameworkContract"), 
						new EqCriterion("project", frameworkContract.getProject())));
		return this;
	}

	public SingleContractQuery serialNumber(String serialNumber) {
		querySettings.containsText("serialNumber", serialNumber);
		return this;

	}

}
