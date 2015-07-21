#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.cooperationOrg;

import ${package}.domain.CooperationOrganization;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-19
 * Time: 上午10:47
 */
public class DisableAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5866641798786561221L;
	private Long id;

    @Override
    public String execute() throws Exception {

        CooperationOrganization org = CooperationOrganization.get(id);

        commonsApplication.disable(org);

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
