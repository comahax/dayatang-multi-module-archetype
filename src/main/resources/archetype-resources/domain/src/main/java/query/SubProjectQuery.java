#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import ${package}.domain.SingleContract;
import ${package}.domain.SubProject;
import com.dayatang.utils.Assert;

public class SubProjectQuery extends BaseQuery<SubProject> {

	private SubProjectQuery() {
		super(QuerySettings.create(SubProject.class));
	}

	/**
	 * 负责机构在这些机构内的单专项目
	 */
	public static SubProjectQuery createResponsibleOf(InternalOrganization scope) {
		Assert.notNull(scope);
		SubProjectQuery query = new SubProjectQuery();
		query.querySettings.and(new GeCriterion("responsibleDivision.leftValue", scope.getLeftValue()), new LeCriterion(
				"responsibleDivision.rightValue", scope.getRightValue()));
		return query;
	}

	public SubProjectQuery project(Project project) {
		if (null == project) {
			return this;
		}
		querySettings.eq("project", project);
		return this;
	}

	public SubProjectQuery projectId(Long projectId) {
		querySettings.eq("project.id", projectId);
		return this;
	}

	/**
	 * 某单项合同下的
	 * 
	 * @param singleContract
	 * @return
	 */
	public SubProjectQuery singleContract(SingleContract singleContract) {
		querySettings.eq("singleContract", singleContract);
		return this;
	}

	/**
	 * 将已竣工的排在前面
	 * 
	 * @return
	 */
	public SubProjectQuery descFinished() {
		querySettings.desc("finishDate");
		return this;
	}

	/**
	 * 已竣工的
	 * 
	 * @return
	 */
	public SubProjectQuery finished() {
		querySettings.notNull("finishDate");
		return this;
	}

	/**
	 * 在建的
	 * 
	 * @return
	 */
	public SubProjectQuery inProcess() {
		querySettings.isNull("finishDate");
		return this;
	}

	/**
	 * 不是任何单项合同下的单点
	 * 
	 * @return
	 */
	public SubProjectQuery independent() {
		querySettings.isNull("singleContract");
		return this;
	}

}
