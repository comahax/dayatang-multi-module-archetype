#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.InternalOrganization;
import ${package}.domain.Project;
import ${package}.domain.Specialty;
import ${package}.domain.SpecialtyProject;
import ${package}.domain.SubProject;
import com.dayatang.utils.Assert;

public class SpecialtyProjectQuery extends BaseQuery<SpecialtyProject> {

	private SpecialtyProjectQuery() {
		super(QuerySettings.create(SpecialtyProject.class));
	}

	public SpecialtyProjectQuery specialtyProjectId(long id) {
		querySettings.eq("id", id);
		return this;
	}

	/**
	 * 负责机构在这些机构内的单专项目
	 */
	public static SpecialtyProjectQuery createResponsibleOf(InternalOrganization scope) {
		Assert.notNull(scope);
		SpecialtyProjectQuery query = new SpecialtyProjectQuery();
		query.querySettings.and(new GeCriterion("subProject.responsibleDivision.leftValue", scope.getLeftValue()),
				new LeCriterion("subProject.responsibleDivision.rightValue", scope.getRightValue()));
		return query;
	}

	public SpecialtyProjectQuery project(Project project) {
		querySettings.eq("subProject.project", project);
		return this;
	}

	public SpecialtyProjectQuery subProject(SubProject subProject) {
		querySettings.eq("subProject", subProject);
		return this;
	}

	public SpecialtyProjectQuery specialty(Specialty specialty) {
		querySettings.eq("specialty", specialty);
		return this;
	}

}
