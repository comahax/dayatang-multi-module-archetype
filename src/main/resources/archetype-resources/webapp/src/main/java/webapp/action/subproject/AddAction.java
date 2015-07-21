#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subproject;

import java.util.List;

import ${package}.domain.Project;
import ${package}.domain.Specialty;
import ${package}.query.ProjectQuery;
import ${package}.query.SpecialtyQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 新建单点工程
 * @author zjzhai
 * 
 */
public class AddAction extends BaseAction {

	private static final long serialVersionUID = -7400678721761154355L;

	private long projectId;
	private Project project;
	private List<Specialty> specialties;

	@Override
	public String execute() throws Exception {

		project = ProjectQuery.getAuthenticateSuccessOf(getGrantedScope(),getCurrentPerson(), projectId);

		specialties = SpecialtyQuery.create().enabled().list();

		return super.execute();
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Specialty> getSpecialties() {
		return specialties;
	}

}
