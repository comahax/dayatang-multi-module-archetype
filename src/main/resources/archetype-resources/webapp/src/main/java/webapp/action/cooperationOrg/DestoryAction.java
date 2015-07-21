#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.cooperationOrg;

import java.util.List;

import ${package}.domain.CooperationOrganization;
import ${package}.domain.Person;
import ${package}.webapp.action.BaseAction;

/**
 * 删除合作单位
 * 
 * @author dzhang
 * 
 */
public class DestoryAction extends BaseAction {

	private static final long serialVersionUID = -1987256446721862770L;

	private Long id = 0l;

	@Override
	public String execute() throws Exception {
		if (null == id || id <= 0l) {
			errorInfo = "合作单位ID不合法";
			return JSON;
		}

		CooperationOrganization cooperationOrganization = CooperationOrganization
				.get(id);

		if (null == cooperationOrganization) {
			errorInfo = "合作单位不存在";
			return JSON;
		}

		try {
			List<Person> people = Person.findByOrganization(cooperationOrganization);
			for (Person each : people) {
				projApplication.removeEntity(each);
			}
			projApplication.removeEntity(cooperationOrganization);
		} catch (Exception e) {
			errorInfo = "该合作单位或机构下的人员已经有关联，无法删除！";
			return JSON;
		}

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
