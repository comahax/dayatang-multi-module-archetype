#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.OrganizationInfo;
import ${package}.domain.Project;
import ${package}.domain.SubContract;
import ${package}.webapp.action.BaseAction;

/**
 * 项目的分包管理 User: zjzhai Date: 13-9-3 Time: 下午2:19
 */
@Result(name = "success", type = "freemarker", location = "subpackages.ftl")
public class SubpackagesAction extends BaseAction {

	private static final long serialVersionUID = 8300575738702361507L;
	/**
	 * 项目的ID
	 */
	private long id = 0l;

	private Project project;

	private Set<OrganizationInfo> cooperations = new HashSet<OrganizationInfo>();

	public String execute() {
		project = getProjectOf(id);
		if (null == project) {
			return NOT_FOUND;
		}
		cooperations = new HashSet<OrganizationInfo>(SubContract.findAllCooperatorsOfProject(project));
		
		return SUCCESS;
	}

	public Set<OrganizationInfo> getCooperations() {
		return cooperations;
	}

	public Project getProject() {
		return project;
	}

	public void setId(long id) {
		this.id = id;
	}

}
