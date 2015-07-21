#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.permission;

import ${package}.domain.Permission;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PermissionDto;

import java.util.List;

/**
 * 只取一级的权限不取下级的
 * User: zjzhai
 * Date: 13-5-2
 * Time: 下午4:42
 */
public class ListJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3714223204874532612L;

	/**
     * 父权限的id
     */
    private Long id = 0l;

    private List<PermissionDto> results;

    @Override
    public String execute() throws Exception {
        Permission parent = Permission.get(Permission.class, id);
        if (null == parent) {
            results = PermissionDto.createBy(Permission.getRoot());
        } else {
            results = PermissionDto.createBy(Permission.findByProperty(Permission.class, "parent", parent));
        }

        return JSON;
    }

    public List<PermissionDto> getResults() {
        return results;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
