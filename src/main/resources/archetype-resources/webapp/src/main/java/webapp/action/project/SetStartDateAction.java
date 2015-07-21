#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import java.util.Date;

import ${package}.domain.Project;
import ${package}.domain.ProjectSerialNumberAssignment;
import ${package}.webapp.action.BaseAction;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 13-8-18 Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public class SetStartDateAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long projectId = 0l;

	private Date startDate;

	public String execute() {
		if (projectId <= 0) {
			errorInfo = getText("NOT_FOUND_PROJECT");
			return JSON;
		}

		if (null == startDate) {
			errorInfo = "开工日期不能为空";
			return JSON;
		}

		Project project = getProjectOf(projectId);
		if (null == project || null != project.getStartDate()) {
			errorInfo = "找不到该项目或者项目已经设置开工日期！";
			return JSON;
		}

		project.setStartDate(startDate);
		ProjectSerialNumberAssignment projectSerialNumberAssignment = project.computeAndSetProjectNumber();
		projApplication.saveSomeEntities(projectSerialNumberAssignment, project);
		return JSON;
	}

	public String errorInfo() {
		return errorInfo;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
