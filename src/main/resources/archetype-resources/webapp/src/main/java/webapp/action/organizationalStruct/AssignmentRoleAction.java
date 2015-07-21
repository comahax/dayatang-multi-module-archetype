#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.*;
import ${package}.query.InternalOrganizationQuery;
import ${package}.query.PersonUserQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.RoleAssignmentDto;

/**
 * 为人员分配角色
 * User: zjzhai
 * Date: 13-5-6
 * Time: 下午9:40
 */
public class AssignmentRoleAction extends BaseAction {

	private static final long serialVersionUID = 278902483922943712L;

	/**
     * 机构ID
     */
    private long scopeId = 0l;

    /**
     * 角色ID
     */
    private long roleId = 0l;

    /**
     * 人员的ID
     */
    private long id = 0l;

    private RoleAssignmentDto result;


    @Override
    public String execute() throws Exception {

        InternalOrganization scope = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), scopeId);
        if (null == scope) {
            return JSON;
        }

        Person person = Person.get(id);
        if (null == person) {
            return JSON;
        }

        Role role = Role.get(roleId);
        if (null == role) {
            return JSON;
        }

        PersonUser user = PersonUserQuery.create().person(person).getSingleResult();
        if (null == user) {
            user = new PersonUser(person.getEmail(), securityApplication.getEncodeSystemDefaultPassword(), person);
            securityApplication.saveEntity(user);
        }
        RoleAssignment rs = securityApplication.assignRoleToUser(user, role, scope);
        result = new RoleAssignmentDto(rs, this);

        return JSON;
    }

    public RoleAssignmentDto getResult() {
        return result;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setScopeId(long scopeId) {
        this.scopeId = scopeId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
