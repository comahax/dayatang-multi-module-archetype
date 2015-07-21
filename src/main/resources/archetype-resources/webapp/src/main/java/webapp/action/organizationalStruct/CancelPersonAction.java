#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import java.util.List;

import ${package}.commons.SystemVariablesUtils;
import ${package}.domain.Person;
import ${package}.domain.PersonUser;
import ${package}.query.PersonQuery;
import ${package}.query.PersonUserQuery;
import ${package}.webapp.ErrorConstants;
import ${package}.webapp.action.BaseAction;

/**
 * 撤消某员工，同时撤消与其关联的用户
 * <p/>
 * User: zjzhai Date: 13-4-10 Time: 上午10:58
 */
public class CancelPersonAction extends BaseAction {

	private static final long serialVersionUID = 3607678288764096676L;

	private Long id = 0l;

	private String errorInfo;

	@Override
	public String execute() throws Exception {

		if (null == id || id <= 0l) {
			return JSON;
		}

		Person person = PersonQuery.create().id(id).getSingleResult();

		if (null == person) {
			errorInfo = getText(ErrorConstants.RESOUCES_NOT_FOUND);
			return JSON;
		}

		if (person.isDisabled()) {
			return JSON;
		}
		String email = person.getEmail();
		
		
		
		cancelPerson(person);

		noticeApplication.notice(email, "撤消帐号通知--" + SystemVariablesUtils.getSysnoticeTitle(), "您在" + SystemVariablesUtils.getTheSystemName() + "的" + person.getEmail() + "已被系统撤消，如有疑问，请咨询本公司管理员。");

		return JSON;
	}

	private void cancelPerson(Person person){
		 List<PersonUser> personUsers = PersonUserQuery.create().person(person).list();
	        for (PersonUser each : personUsers) {
	        	 each.disable();
	            projApplication.saveEntity(each);
	        }
	        person.disable();
	        projApplication.saveEntity(person);
		
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
