#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.person;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstanceQuery;

import ${package}.domain.Person;
import ${package}.domain.PersonUser;
import ${package}.domain.RoleAssignment;
import ${package}.process.ProcessConstants;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.process.BaseProcessAction;

/**
 * 从数据库里删除人 User: zjzhai Date: 13-7-31 Time: 下午4:11
 */
public class DestroyAction extends BaseProcessAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6372423514342193435L;
	private Long id = 0l;

	@Override
	public String execute() throws Exception {

		if (null == id || id <= 0l) {
			return JSON;
		}

		Person person = PersonQuery.create().subordinateWithSelf(getGrantedScope()).id(id).getSingleResult();

		if (null == person) {
			return JSON;
		}

		try {

			List<PersonUser> users = PersonUser.findByPerson(person);
			List<RoleAssignment> assignments = new ArrayList<RoleAssignment>();
			if (users != null && !users.isEmpty()) {
				for (PersonUser user : users) {
					ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().variableValueEqualsIgnoreCase(ProcessConstants.INITIATOR_USERNAME, user.getUsername());
					if (query.count() > 0) {
						throw new Exception("");
					}

					HistoricProcessInstanceQuery historicQuery = historyService.createHistoricProcessInstanceQuery().finished().orderByProcessDefinitionId().orderByProcessInstanceStartTime().desc();
					if (historicQuery.count() > 0) {
						throw new Exception("");
					}
					assignments.addAll(RoleAssignment.findByUser(user));
				}
			}
			securityApplication.destroyPerson(person, users, assignments);
		} catch (Exception e) {
			errorInfo = getText("DELETE_PERSON_FAIL");
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
