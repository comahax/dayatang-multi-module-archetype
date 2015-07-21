#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.user;

import java.util.List;

import ${package}.domain.RoleAssignment;
import ${package}.webapp.action.BaseAction;

/**
 * 切换角色
 *
 * @author zjzhai
 */
public class SwitchRoleAction extends BaseAction {

    private static final long serialVersionUID = -1901515838415896263L;

    // 角色分配ID
    private long raid = 0L;


    public String execute() {

        RoleAssignment roleAssignment = RoleAssignment.get(RoleAssignment.class, raid);

        if (null == roleAssignment) {
            return WORKTABLE;
        }

        List<RoleAssignment> list = RoleAssignment.findByUser(getCurrentUser());

        //检测是否有那个角色分配
        if (null != list && list.size() > 0 && list.contains(roleAssignment)) {
            getSession().put(ROLE_ASSIGNMENT_KEY, raid);
            return WORKTABLE;
    }

    return WORKTABLE;
}

    public void setRaid(long raid) {
        this.raid = raid;
    }

}
