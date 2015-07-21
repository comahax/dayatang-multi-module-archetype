#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import java.util.Date;

import ${package}.domain.Project;
import ${package}.webapp.action.BaseAction;

/**
 * 设置计划完成日期
 * 
 * @author zjzhai
 * 
 */
public class SetPredictFinishDateAction extends BaseAction {

	private static final long serialVersionUID = 9198467402119472285L;

	private long projectId = 0l;

	private Date predictFinishDate;

	public String execute() {

		if (null == predictFinishDate) {
			errorInfo = "计划完工日期不能为空";
			return JSON;
		}

		Project project = getProjectOf(projectId);

		if (null == project || project.getPredictFinishDate() != null) {
			errorInfo = "找不到项目或者项目已经设置计划完成日期！";
			return JSON;
		}
		project.setPredictFinishDate(predictFinishDate);
		projApplication.saveEntity(project);

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public void setPredictFinishDate(Date predictFinishDate) {
		this.predictFinishDate = predictFinishDate;
	}

}
