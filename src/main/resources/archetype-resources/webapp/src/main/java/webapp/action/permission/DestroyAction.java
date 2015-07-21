#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.permission;

import ${package}.domain.Permission;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-5-2
 * Time: 下午9:58
 */
public class DestroyAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7340843126651230072L;
	/**
     * 权限的ID
     */
    private Long id;

    @Override
    public String execute() throws Exception {

        Permission permission = Permission.get(Permission.class, id);

        securityApplication.removeEntity(permission);


        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
