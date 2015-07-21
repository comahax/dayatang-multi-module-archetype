#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.project;

import java.util.ArrayList;
import java.util.List;

import com.dayatang.domain.AbstractEntity;
import ${package}.domain.InternalOrganization;
import ${package}.domain.OutputValue;
import ${package}.domain.Project;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 更改项目的负责机构
 * 
 * @author zjzhai
 * 
 */
public class ChangeResponsibleDivisionAction extends BaseAction {

	private static final long serialVersionUID = -4018781216410726165L;

	private long id = 0l;

	private long responsibleDivisionId = 0l;

	private InternalOrganization org;

	public String execute() {

		if (id <= 0l) {
			errorInfo = "该项目不存在";
			return JSON;
		}

		Project project = getProjectOf(id);

		if (null == project) {
			errorInfo = "该项目不存在";
			return JSON;
		}

		if (!project.isConstructing()) {
			errorInfo = "在建项目才能修改负责机构！";
			return JSON;
		}

		org = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), responsibleDivisionId);

		if (null == org) {
			errorInfo = "该机构不存在或您当前角色无法访问该机构。";
			return JSON;
		}

		List<OutputValue> outputvalues = OutputValue.findBy(project);

		project.setResponsibleDivision(org);
		for (OutputValue each : outputvalues) {
			each.setResponsibleDivision(org);
		}

		List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
		entities.add(project);
		entities.addAll(outputvalues);
		try {
			projApplication.saveSomeEntities(entities.toArray(new AbstractEntity[outputvalues.size() + 1]));
		} catch (Exception e) {
			sendExceptionMsgAdmin(e.getMessage());
			errorInfo = "保存失败！错误信息已发送给系统管理员。";
			return JSON;
		}

		return JSON;
	}

	public String getFullName() {
		if (null == org) {
			return "";
		}
		return org.getFullName();
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setResponsibleDivisionId(long responsibleDivisionId) {
		this.responsibleDivisionId = responsibleDivisionId;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

}
