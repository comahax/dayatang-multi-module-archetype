#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.Person;
import ${package}.domain.SupervisorOrganization;
import ${package}.webapp.action.person.AddBaseAction;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午2:12
 */
public class AddPersonAction extends AddBaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7017917002103195348L;
	private Long id = 0l;

    public String execute() throws Exception {

        SupervisorOrganization org = SupervisorOrganization.get(id);

        if(null == org){
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
