#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.sys;

import ${package}.domain.Role;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-2
 * Time: 下午3:52
 */
public class LogsJsonAction extends BaseAction {


	private static final long serialVersionUID = 5841504289985945908L;
	private long total = 0;

    @Override
    public String execute() throws Exception {

        if(!Role.SYSTEM_ROLE_ID.equals(getRole().getId())){
            return NOT_FOUND;
        }




        return JSON;
    }


    public long getTotal() {
        return total;
    }
}
