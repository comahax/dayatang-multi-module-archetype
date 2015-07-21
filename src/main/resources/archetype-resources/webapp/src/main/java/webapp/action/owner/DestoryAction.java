#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.owner;


import java.util.List;

import ${package}.domain.OwnerOrganization;
import ${package}.domain.Person;
import ${package}.webapp.action.BaseAction;

/**
 * 删除客户单位
 * @author dzhang
 * 
 */
public class DestoryAction extends BaseAction {


	private static final long serialVersionUID = 4065326727414599730L;

	private Long id = 0l;


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(null == id || id <= 0l){
			errorInfo = "客户单位ID不合法";
			return JSON;
		}

		OwnerOrganization ownerOrganization = OwnerOrganization.get(id);

		if(null == ownerOrganization){
			errorInfo = "客户单位不存在";
			return JSON;
		}

		try {
			List<Person> people = Person.findByOrganization(ownerOrganization);
			for (Person each : people) {
				projApplication.removeEntity(each);
			}
			projApplication.removeEntity(ownerOrganization);
		} catch (Exception e) {
			// TODO: handle exception
			errorInfo = "该客户单位或机构下的人员已经有关联，无法删除！";
			return JSON;
		}

		return JSON;
	}

	public String getErrorInfo(){
		return errorInfo;
	}


}
