#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.Person;
import ${package}.domain.SupervisorOrganization;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;

/**
 * User: zjzhai
 * Date: 13-4-19
 * Time: 上午9:48
 */
public class RemovePersonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3926038984712067446L;

	private Long orgId = 0l;

    private Long id = 0l;

    @Override
    public String execute() throws Exception {

        SupervisorOrganization org = SupervisorOrganization.get(orgId);

        if(null == org){
            return JSON;
        }


        Person person = PersonQuery.create().organization(org).id(id).getSingleResult();

        if(null != person){
            commonsApplication.removeEntity(person);
        }

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
