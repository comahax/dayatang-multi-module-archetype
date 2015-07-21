#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.permission;

import ${package}.domain.Permission;
import ${package}.webapp.dto.PermissionDto;
import org.apache.commons.lang3.StringUtils;

/**
 * User: zjzhai
 * Date: 13-5-2
 * Time: 下午5:41
 */
public class AddAction extends AddBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4364648408195968263L;
	/**
     * 父权限的ID
     */
    private Long id = 0l;

    @Override
    public String execute() throws Exception {

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(description)) {
            return JSON;
        }

        Permission parent = Permission.get(Permission.class, id);
        Permission permission = new Permission();
        permission.setParent(parent);
        permission.setName(name);
        permission.setDescription(description);
        permission.setSortOrder(sortOrder);

        securityApplication.saveEntity(permission);

        result = new PermissionDto(permission);

        return JSON;
    }

    public PermissionDto getResult() {
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
