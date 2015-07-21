#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.designOrg;

import ${package}.domain.DesignOrganization;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-19
 * Time: 上午10:47
 */
public class DisableAction extends BaseAction {

	private static final long serialVersionUID = -3306605905221467126L;
	private Long id;

    @Override
    public String execute() throws Exception {

        DesignOrganization org = DesignOrganization.get(id);

        commonsApplication.disable(org);

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
