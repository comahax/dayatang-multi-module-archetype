#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.ChengBao;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import ${package}.domain.SubContract;
import com.dayatang.utils.Assert;

/**
 * 单项合同查询
 * 
 * @author zjzhai
 * 
 */
public class SubContractQuery extends BaseQuery<SubContract> {

	private SubContractQuery() {
		super(QuerySettings.create(SubContract.class));
	}

	/**
	 * 负责机构在这些机构内的合同
	 */
	public static SubContractQuery grantedScopeIn(InternalOrganization scope) {
		Assert.notNull(scope);
		SubContractQuery query = new SubContractQuery();
		query.querySettings.and(new GeCriterion("grantedScope.leftValue", scope.getLeftValue()), new LeCriterion(
				"grantedScope.rightValue", scope.getRightValue()));
		return query;
	}

	public static SubContractQuery create() {
		return new SubContractQuery();
	}

	public SubContractQuery project(Project project) {
		querySettings.eq("project", project);
		return this;
	}

	public SubContractQuery chengBao(ChengBao chengBao) {
		querySettings.eq("chengBao", chengBao);
		return this;
	}

	public SubContractQuery id(long id) {
		querySettings.eq("id", id);
		return this;
	}

}
