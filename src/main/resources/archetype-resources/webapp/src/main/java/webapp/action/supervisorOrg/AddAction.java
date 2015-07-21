#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.SupervisorOrganization;
import ${package}.webapp.action.organization.OuterOrgAddBaseAction;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 上午11:22
 */
public class AddAction extends OuterOrgAddBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8537810967354389792L;

	@Override
    public String execute() throws Exception {
        SupervisorOrganization org = null;
        //编辑机构
        if (id > 0) {
            org = SupervisorOrganization.get(id);
            if (null == org) {
                return JSON;
            }
        } else {
            org = new SupervisorOrganization();
        }
        init(org);
        commonsApplication.saveEntity(org);
        return JSON;
    }

}
