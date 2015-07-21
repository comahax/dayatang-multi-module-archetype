#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.designOrg;

import ${package}.domain.DesignOrganization;
import ${package}.webapp.action.organization.OuterOrgAddBaseAction;

/**
 * User: zjzhai
 * Date: 13-4-19
 * Time: 上午10:21
 */
public class AddAction extends OuterOrgAddBaseAction {


    /**
	 * 
	 */
	private static final long serialVersionUID = 8899189635157624081L;

	@Override
    public String execute() throws Exception {
        DesignOrganization org = null;
        //编辑机构
        if (id > 0) {
            org = DesignOrganization.get(id);
            if (null == org) {
                return JSON;
            }
        } else {
            org = new DesignOrganization();
        }

        init(org);

        commonsApplication.saveEntity(org);


        return JSON;
    }
}
