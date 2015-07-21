#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.SupervisorOrganization;
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
	private static final long serialVersionUID = -6423249603847114852L;
	private Long id;

    @Override
    public String execute() throws Exception {

        SupervisorOrganization org = SupervisorOrganization.get(id);

        commonsApplication.disable(org);

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
