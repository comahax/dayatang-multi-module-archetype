#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.permission;

import ${package}.domain.Permission;
import ${package}.webapp.dto.PermissionDto;
import org.apache.commons.lang3.StringUtils;

/**
 * User: zjzhai
 * Date: 13-5-3
 * Time: 上午9:19
 */
public class EditAction extends AddBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1087543086305282754L;
	/**
     * 待修改的权限的ID
     */
    private Long id = 0l;

    @Override
    public String execute() throws Exception {

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(description)) {
            return JSON;
        }
        Permission permission = Permission.get(Permission.class, id);
        permission.setName(name);
        permission.setDescription(description);
        permission.setSortOrder(sortOrder);
        securityApplication.saveEntity(permission);
        result = new PermissionDto(permission);
        return JSON;
    }

    public PermissionDto getResult(){
        return result;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
