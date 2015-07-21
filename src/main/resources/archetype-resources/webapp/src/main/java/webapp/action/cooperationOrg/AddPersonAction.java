#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.cooperationOrg;

import ${package}.domain.CooperationOrganization;
import ${package}.domain.Person;
import ${package}.webapp.action.person.AddBaseAction;

/**
 * 添加合作单位人员
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午2:12
 */
public class AddPersonAction extends AddBaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6699293610346523365L;
	private Long id = 0l;

    public String execute() throws Exception {

        CooperationOrganization org = CooperationOrganization.get(id);

        if (null == org) {
            return JSON;
        }
        Person person = new Person();
        init(person);
        person.setOrganization(org);
        commonsApplication.saveEntity(person);
        return JSON;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
