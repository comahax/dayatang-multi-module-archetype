#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.ownerPerson;

import ${package}.domain.OwnerOrganization;
import ${package}.domain.Person;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 删除某联系人
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午4:50
 */
public class RemoveAction extends BaseAction {

	private static final long serialVersionUID = -301602719298986742L;

	/**
     * 业主单位的id
     */
    private Long orgId = 0l;

    private Long id = 0l;

    @Override
    public String execute() throws Exception {

        Person person = PersonQuery.create().organization(OwnerOrganization.get(orgId)).id(id).getSingleResult();

        if (null != person) {
            commonsApplication.removeEntity(person);
        }

        return JSON;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
