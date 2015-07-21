#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.contract;

import java.util.List;

import ${package}.domain.Project;
import ${package}.query.ContractQuery;
import ${package}.query.ProjectQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.ContractDto;

/**
 * 合同列表json
 * 
 * @author zjzhai
 * 
 */
public class ListForProjectAction extends BaseAction {

	private static final long serialVersionUID = 8640611372002992847L;

	private long projectId = 0l;

	private List<ContractDto> results;

	public String execute() throws Exception {

		Project project = ProjectQuery.getAuthenticateSuccessOf(getGrantedScope(),getCurrentPerson(), projectId);

		results = ContractDto.createBy(ContractQuery.findAllOf(project, getGrantedScope()),this);

		return JSON;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public List<ContractDto> getResults() {
		return results;
	}

}
