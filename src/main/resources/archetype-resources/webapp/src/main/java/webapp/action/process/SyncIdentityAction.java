#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.process;

import org.activiti.engine.identity.Group;

import ${package}.domain.PersonUser;
import ${package}.domain.Role;
import ${package}.domain.RoleAssignment;
import ${package}.domain.User;

/**
 * 临时工具类，用于同步系统用户系统和Activiti用户系统
 * @author yyang
 *
 */
public class SyncIdentityAction extends BaseProcessAction {

	private static final long serialVersionUID = -4823862633905721672L;

	@Override
	public String execute() throws Exception {
		clearExistedUsersAndGroups();
		syncUsers();
		syncGroups();
		syncMembership();
		return SUCCESS;
	}

	private void clearExistedUsersAndGroups() {
		clearExistedUsers();
		clearExistedGroups();
	}

	private void clearExistedUsers() {
		for (org.activiti.engine.identity.User user : identityService.createUserQuery().list()) {
			identityService.deleteUser(user.getId());
		}
	}

	private void clearExistedGroups() {
		for (Group group : identityService.createGroupQuery().list()) {
			identityService.deleteGroup(group.getId());
		}
	}

	private void syncUsers() {
		for (User each : commonsApplication.findAllEntities(User.class)) {
			PersonUser systemUser = (PersonUser) each;
			org.activiti.engine.identity.User user = identityService.newUser(systemUser.getUsername());
			user.setEmail(systemUser.getEmail());
			String name = systemUser.getPerson().getName();
			user.setFirstName(name.substring(1));
			user.setLastName(name.substring(0, 1));
			user.setPassword(systemUser.getPassword());
			identityService.saveUser(user);
		}
	}

	private void syncGroups() {
		for (Role role : commonsApplication.findAllEntities(Role.class)) {
			Group group = identityService.newGroup(role.getName());
			group.setName(role.getDescription());
			identityService.saveGroup(group);
		}
	}

	private void syncMembership() {
		for (RoleAssignment assignment : commonsApplication.findAllEntities(RoleAssignment.class)) {
			identityService.createMembership(assignment.getUser().getUsername(), assignment.getRole().getName());
		}
	}
	
}
