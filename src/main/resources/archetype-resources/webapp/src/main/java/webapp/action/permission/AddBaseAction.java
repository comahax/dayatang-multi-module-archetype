#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.permission;

import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PermissionDto;

/**
 * User: zjzhai
 * Date: 13-5-3
 * Time: 上午9:18
 */
public class AddBaseAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1276151771023366660L;

	protected String name;

    protected String description;

    protected int sortOrder = 0;

    protected PermissionDto result;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
