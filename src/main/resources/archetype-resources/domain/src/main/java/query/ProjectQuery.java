#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.*;
import ${package}.domain.*;
import com.dayatang.utils.Assert;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目查询器
 * 
 * @author zjzhai
 */
public class ProjectQuery extends BaseQuery<Project> {

	public ProjectQuery() {
		super(QuerySettings.create(Project.class));
	}

	/**
	 * 某个人管辖范围内的，不包括别人的草稿
	 * 
	 * @param scope
	 * @param who
	 * @return
	 */
	public static ProjectQuery responsibleOf(InternalOrganization scope, Person who) {
		Assert.notNull(scope);
		ProjectQuery query = new ProjectQuery();
		query.querySettings.and(new AndCriterion(new GeCriterion("responsibleDivision.leftValue", scope.getLeftValue()), new LeCriterion("responsibleDivision.rightValue", scope.getRightValue())), new OrCriterion(new EqCriterion("creator", who),
				new AndCriterion(new NotEqCriterion("status", ProjectStatus.DRAFT), new NotEqCriterion("creator", who))));
		return query;
	}

	/**
	 * 返回认证通过的 project
	 * 
	 * @param scope
	 * @param projectId
	 * @return
	 */
	public static Project getAuthenticateSuccessOf(InternalOrganization scope, Person who, long projectId) {
		return ProjectQuery.responsibleOf(scope, who).id(projectId).enabled().getSingleResult();
	}

	public ProjectQuery nameLike(String name) {
		querySettings.containsText("name", name);
		return this;
	}

	public ProjectQuery projectStatus(ProjectStatus status) {
		querySettings.eq("status", status);
		return this;
	}

	/**
	 * 是草稿或者未设置以哪家公司名义开展
	 * 
	 * @return
	 */
	public ProjectQuery draftOrIsNullConstructingOrg() {
		querySettings.or(new EqCriterion("status", ProjectStatus.DRAFT), new IsNullCriterion("constructingOrg"));
		return this;
	}

	/**
	 * 负责单位
	 * 
	 * @param id
	 * @return
	 */
	public ProjectQuery responsibleDivision(Long id) {
		querySettings.eq("responsibleDivision.id", id);
		return this;
	}

	/**
	 * 查询 internalOrganization下所有的直属及间接子机构
	 * 
	 * @param internalOrganization
	 * @return
	 */
	public ProjectQuery subordinateOf(InternalOrganization internalOrganization) {
		querySettings.and(new GeCriterion("responsibleDivision.leftValue", internalOrganization.getLeftValue()), new LeCriterion("responsibleDivision.rightValue", internalOrganization.getRightValue()));
		return this;
	}

	public ProjectQuery isBusinessOperationsabled() {
		querySettings.or(new EqCriterion("status", ProjectStatus.APPROVED), new EqCriterion("status", ProjectStatus.CONSTRUCTING), new EqCriterion("status", ProjectStatus.FINISHED));
		return this;
	}

	public ProjectQuery excludeStatus(ProjectStatus status) {
		ProjectStatus[] statuses = ProjectStatus.values();
		statuses = ArrayUtils.remove(statuses, ArrayUtils.indexOf(statuses, status));
		List<EqCriterion> eqs = new ArrayList<EqCriterion>();
		for (ProjectStatus each : statuses) {
			EqCriterion eq = new EqCriterion("status", each);
			eqs.add(eq);
		}
		querySettings.or(eqs.toArray(new EqCriterion[eqs.size() - 1]));
		return this;
	}

	/**
	 * 未设置以哪家公司名义开展
	 * 
	 * @return
	 */
	public ProjectQuery isNullConstructingOrg() {
		querySettings.isNull("constructingOrg");
		return this;
	}

	public ProjectQuery id(long projectId) {
		querySettings.eq("id", projectId);
		return this;
	}

	public ProjectQuery closedIf(Boolean closed) {
		if (null == closed) {
			return this;
		}
		return closed ? closed() : notClosed();
	}

	public ProjectQuery closed() {
		querySettings.notNull("closeDate");
		return this;
	}

	public ProjectQuery creator(Person creator) {
		querySettings.eq("creator", creator);
		return this;
	}

	public ProjectQuery notClosed() {
		querySettings.isNull("closeDate");
		return this;
	}

	public ProjectQuery areaEq(Area area) {
		if (null == area) {
			return this;
		}
		querySettings.eq("area", area);
		return this;
	}

	public ProjectQuery thereAreSubProject() {
		querySettings.notEmpty("subProjects");
		return this;
	}

	/**
	 * 区域内
	 * 
	 * @param area
	 * @return
	 */
	public ProjectQuery areaIn(Area area) {
		if (null == area) {
			return this;
		}
		querySettings.in("area", area.getChildren());
		return this;
	}

	public ProjectQuery descCreated() {
		querySettings.desc("created");
		return this;
	}

}
