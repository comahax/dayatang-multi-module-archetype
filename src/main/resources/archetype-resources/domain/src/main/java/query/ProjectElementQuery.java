#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;


import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.InternalOrganization;
import ${package}.domain.ProjectElement;

public class ProjectElementQuery extends BaseQuery<ProjectElement> {

	private ProjectElementQuery() {
		super(QuerySettings.create(ProjectElement.class));
	}

	/**
	 * 负责机构在这些机构内的项目
	 */
	public static ProjectElementQuery responsibleOf(InternalOrganization scope) {
		ProjectElementQuery query = new ProjectElementQuery();
		query.querySettings.and(new GeCriterion("responsibleDivision.leftValue", scope.getLeftValue()), new LeCriterion(
				"responsibleDivision.rightValue", scope.getRightValue()));
		return query;
	}

	/**
	 * 返回认证通过的 project
	 *
	 * @return
	 */
	public static ProjectElement getAuthenticateSuccessOf(InternalOrganization scope, long projectElementId) {
		return ProjectElementQuery.responsibleOf(scope).id(projectElementId).enabled().getSingleResult();
	}

	public ProjectElementQuery id(long projectElementId) {
		querySettings.eq("id", projectElementId);
		return this;
	}

}
