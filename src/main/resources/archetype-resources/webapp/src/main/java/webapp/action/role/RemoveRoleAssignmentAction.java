#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.role;

import ${package}.domain.RoleAssignment;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-5-7
 * Time: 下午5:18
 */
public class RemoveRoleAssignmentAction extends BaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = -8958250239628215768L;
	/**
     * 角色分配的ID
     */
    private long id = 0l;

    @Override
    public String execute() throws Exception {

        if (id <= 0l) {
            return JSON;
        }
        RoleAssignment roleAssignment = RoleAssignment.get(RoleAssignment.class, id);

        if (null == roleAssignment) {
            return JSON;
        }

        securityApplication.removeEntity(roleAssignment);

        return JSON;
    }


    public void setId(long id) {
        this.id = id;
    }
}
