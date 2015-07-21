#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import java.util.Arrays;
import java.util.List;

import ${package}.domain.ProjectStatus;
import ${package}.webapp.action.BaseAction;

public class ListAction extends BaseAction {

	private static final long serialVersionUID = -3127641791742486498L;

	public String execute() {
		return SUCCESS;
	}

	public List<ProjectStatus> getProjectStatues() {
		return Arrays.asList(ProjectStatus.values());
	}

}
