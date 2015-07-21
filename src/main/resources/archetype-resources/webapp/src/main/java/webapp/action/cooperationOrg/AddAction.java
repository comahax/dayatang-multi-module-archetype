#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.cooperationOrg;

import ${package}.domain.CooperationOrganization;
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
	private static final long serialVersionUID = 4303659308651017588L;

	@Override
    public String execute() throws Exception {

        CooperationOrganization org = null;
        //编辑机构
        if (id > 0) {
            org = CooperationOrganization.get(id);
            if (null == org) {
                return JSON;
            }
        } else {
            org = new CooperationOrganization();
        }
        init(org);

        commonsApplication.saveEntity(org);


        return JSON;
    }
}
