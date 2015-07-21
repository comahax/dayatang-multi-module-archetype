#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.role;

import java.util.List;

import ${package}.webapp.dto.PermissionDto;
import ${package}.domain.Role;
import ${package}.webapp.action.BaseAction;

/**
 * 得到某个角色所有权限的JSON
 *
 * @author zjzhai
 */

public class PermissionsOfRoleAction extends BaseAction {

    private static final long serialVersionUID = 4336805404967645369L;
    /**
     * 角色ID
     */
    private long id = 0L;

    private List<PermissionDto> results;

    public String execute() {


        Role role = Role.get(id);

        if (null == role) {
            return JSON;
        }
        results = PermissionDto.createBy(role.getPermissions());

        return JSON;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PermissionDto> getResults() {
        return results;
    }
}
