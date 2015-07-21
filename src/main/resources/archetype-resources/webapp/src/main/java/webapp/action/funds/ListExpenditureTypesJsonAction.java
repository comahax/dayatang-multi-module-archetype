#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.funds;

import java.util.ArrayList;
import java.util.List;

import ${package}.domain.Project;
import ${package}.query.ProjectQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ExpenditureTypeDto;

/**
 * 列出某项目成本预算的json数据
 * 
 * @author zjzhai
 * 
 */
public class ListExpenditureTypesJsonAction extends BaseAction {
	private static final long serialVersionUID = 4695213070046575575L;

	private long projectId = 0;

	private List<ExpenditureTypeDto> results = new ArrayList<ExpenditureTypeDto>();

	public String execute() throws Exception {

		Project project = ProjectQuery.getAuthenticateSuccessOf(getGrantedScope(),getCurrentPerson(), projectId);

		results = ExpenditureTypeDto.createBy(project == null ? null : project.getBudgets());

		return JSON;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public List<ExpenditureTypeDto> getResults() {
		return results;
	}

}
