#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.role;

import ${package}.domain.Role;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.RoleDto;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-3
 * Time: 上午11:08
 */
public class ListJsonAction extends BaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = 7444901674901320004L;
	private List<RoleDto> results;

    @Override
    public String execute() throws Exception {

        results = RoleDto.createBy(Role.findAll(Role.class));

        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<RoleDto> getResults() {
        return results;
    }
}