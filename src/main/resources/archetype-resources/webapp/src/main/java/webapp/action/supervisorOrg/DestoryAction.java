#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import java.util.List;

import ${package}.domain.Person;
import ${package}.domain.SupervisorOrganization;
import ${package}.webapp.action.BaseAction;

public class DestoryAction extends BaseAction {

	/**
	 * 删除监理单位
	 * @author dzhang
	 */
	private static final long serialVersionUID = 4986301306254376098L;
	
	private Long id = 0l;



	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(null == id || id <= 0l){
			errorInfo = "监理单位ID不合法";
			return JSON;
		}
		SupervisorOrganization supervisorOrganization = SupervisorOrganization.get(id);
		if(null == supervisorOrganization){
			errorInfo = "监理单位不存在";
			return JSON;
		}
		try {
			List<Person> people = Person.findByOrganization(supervisorOrganization);
			for (Person each : people) {
				projApplication.removeEntity(each);
			}
			projApplication.removeEntity(supervisorOrganization);
		} catch (Exception e) {
			// TODO: handle exception
			errorInfo = "该监理单位或机构下的人员已经有关联，无法删除！";
			return JSON;
		}
		return JSON;
	}
	
	public String getErrorInfo(){
		return errorInfo;
	}
	
	public void setId(Long id) {
		this.id = id;
	}



}
