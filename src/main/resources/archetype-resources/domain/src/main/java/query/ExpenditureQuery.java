#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.Expenditure;
import ${package}.domain.ExpenditureType;
import ${package}.domain.Monthly;
import ${package}.domain.Project;
import ${package}.domain.SubProject;

/**
 * 支出查询器
 * 
 * @author zjzhai
 * 
 */
public class ExpenditureQuery extends BaseQuery<Expenditure> {

	private ExpenditureQuery() {
		super(QuerySettings.create(Expenditure.class));
	}

	public static ExpenditureQuery create() {
		return new ExpenditureQuery();
	}

	public ExpenditureQuery ascSpendDate() {
		querySettings.asc("spendDate");
		return this;
	}

	public ExpenditureQuery monthly(Monthly monthly) {
		querySettings.eq("monthly.year", monthly.getYear());
		querySettings.eq("monthly.month", monthly.getMonth());
		return this;
	}

	public ExpenditureQuery subProject(SubProject subProject) {
		querySettings.eq("subProject", subProject);
		return this;
	}

	public ExpenditureQuery project(Project project) {
		querySettings.eq("project", project);
		return this;
	}

	public ExpenditureQuery expenditureType(ExpenditureType expenditureType) {
		querySettings.eq("expenditureType", expenditureType);
		return this;
	}

}
