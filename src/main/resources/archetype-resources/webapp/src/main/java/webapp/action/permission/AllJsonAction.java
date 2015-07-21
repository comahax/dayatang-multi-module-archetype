#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.permission;

import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PermissionDto;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-5-3
 * Time: 下午2:41
 */
public class AllJsonAction extends BaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = -3444763383872241542L;
	private List<PermissionDto> results;

    @Override
    public String execute() throws Exception {

        results = PermissionDto.allPermissions();

        return JSON;
    }

    public List<PermissionDto> getResults() {
        return results;
    }
}
