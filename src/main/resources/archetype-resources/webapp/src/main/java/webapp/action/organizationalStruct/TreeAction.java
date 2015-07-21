#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午3:28
 */
public class TreeAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6491255290556767992L;
	private Long currentOrg = 0l;

    public String execute() throws Exception {
        currentOrg = getCurrentPerson().getOrganization().getId();
        return SUCCESS;
    }

    public Long getCurrentOrg() {
        return currentOrg;
    }
}
