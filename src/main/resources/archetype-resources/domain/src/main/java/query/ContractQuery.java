#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.Area;
import ${package}.domain.Contract;
import ${package}.domain.ContractCategory;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import com.dayatang.utils.Assert;

public class ContractQuery extends BaseQuery<Contract> {

	private ContractQuery() {
		super(QuerySettings.create(Contract.class));
	}

	/**
	 * 哪年签定的合同
	 * 
	 * @param signyear
	 */
	public ContractQuery signYear(String signYear) {
		if (signYear == null) {
			return this;
		}
		Date from = com.dayatang.utils.DateUtils.parseDate(signYear + "-01-01");
		Date to = com.dayatang.utils.DateUtils.parseDate(signYear + "-12-31");
		querySettings.between("signDate", from, to);
		return this;
	}

	/**
	 * 在某个区域中的
	 * 
	 * @param area
	 * @return
	 */
	public ContractQuery at(Area area) {
		if (area == null) {
			return this;
		}
		querySettings.eq("project.area", area);
		return this;
	}

	/**
	 * 在某个区域范围内的
	 * 
	 * @param areas
	 * @return
	 */
	public ContractQuery at(Collection<Area> areas) {
		if (areas == null || areas.isEmpty()) {
			return this;
		}
		querySettings.in("project.area", areas);
		return this;
	}

	/**
	 * 负责机构在这些机构内的合同
	 */
	public static ContractQuery grantedScopeIn(InternalOrganization scope) {
		Assert.notNull(scope);
		ContractQuery query = new ContractQuery();
		query.querySettings.and(new GeCriterion("grantedScope.leftValue", scope.getLeftValue()), new LeCriterion(
				"grantedScope.rightValue", scope.getRightValue()));
		return query;
	}

	public ContractQuery project(Project project) {
		querySettings.eq("project", project);
		return this;
	}

	public ContractQuery contractId(long id) {
		querySettings.eq("id", id);
		return this;
	}

	/**
	 * 在机构范围内找出某个项目下所有合同
	 * 
	 * @param project
	 *            机构范围
	 * @return
	 */
	public static List<Contract> findAllOf(Project project, InternalOrganization scope) {
		ContractQuery contractQuery = ContractQuery.grantedScopeIn(scope).project(project);
		return contractQuery.enabled().list();
	}

	/**
	 * 根据合同类型来查询
	 * 
	 * @param contractCategory
	 * @return
	 */
	public ContractQuery contractCategory(ContractCategory contractCategory) {
		querySettings.eq("contractCategory", contractCategory);
		return this;
	}

}
