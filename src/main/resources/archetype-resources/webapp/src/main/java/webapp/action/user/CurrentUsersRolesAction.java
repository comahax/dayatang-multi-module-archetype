#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.user;

import java.util.HashSet;
import java.util.Set;

import ${package}.domain.RoleAssignment;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.RoleAssignmentDto;

/**
 * 得到某用户的所有角色
 *
 * @author zjzhai
 */
public class CurrentUsersRolesAction extends BaseAction {

    private static final long serialVersionUID = 4829378953607459924L;

    /**
     * 当前角色
     */
    private RoleAssignmentDto currentRole;

    private Set<RoleAssignmentDto> results = new HashSet<RoleAssignmentDto>();

    public String execute() {
        for (RoleAssignment each : RoleAssignment.findByUser(getCurrentUser())) {
            results.add(new RoleAssignmentDto(each,this));
        }

        currentRole = new RoleAssignmentDto(getAssignment(),this);

        return JSON;
    }

    public RoleAssignmentDto getCurrentRole() {
        return currentRole;
    }

    public Set<RoleAssignmentDto> getResults() {
        return results;
    }
}
