#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.designOrg;

import java.util.List;

import ${package}.domain.DesignOrganization;
import ${package}.domain.Person;
import ${package}.webapp.action.BaseAction;

public class DestoryAction extends BaseAction {

	/**
	 * 删除设计单位
	 * 
	 * @author dzhang
	 */
	private static final long serialVersionUID = 4592795225088332619L;

	private Long id = 0l;

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if (null == id || id <= 0l) {
			errorInfo = "设计单位ID不合法";
			return JSON;
		}
		DesignOrganization designOrganization = DesignOrganization.get(id);
		if (null == designOrganization) {
			errorInfo = "设计单位不存在";
			return JSON;
		}
		try {
			List<Person> people = Person.findByOrganization(designOrganization);
			for (Person each : people) {
				projApplication.removeEntity(each);
			}
			projApplication.removeEntity(designOrganization);
		} catch (Exception e) {
			// TODO: handle exception
			errorInfo = "该设计单位或机构下的人员已经有关联，无法删除！";
			return JSON;
		}
		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

}
