#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.role;

import ${package}.domain.Role;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.RoleDto;

import java.util.List;

/**
 * 列出所有可分配的角色
 * User: zjzhai
 * Date: 13-4-9
 * Time: 上午10:19
 */
public class AssignedJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7563921705380484890L;

	private int total = 0;

    private List<RoleDto> results;

    @Override
    public String execute() throws Exception {
        results = RoleDto.createBy(Role.assignedList());
        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<RoleDto> getResults() {
        return results;
    }

    public int getTotal() {
        return total;
    }

}
