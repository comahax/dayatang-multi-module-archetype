#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.sys;

import ${package}.domain.Role;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-2
 * Time: 下午3:44
 */
public class LogsAction extends BaseAction {

	private static final long serialVersionUID = 7185811652278712926L;

	@Override
    public String execute() throws Exception {

        if(!Role.SYSTEM_ROLE_ID.equals(getRole().getId())){
            return NOT_FOUND;
        }
        return SUCCESS;
    }
}
