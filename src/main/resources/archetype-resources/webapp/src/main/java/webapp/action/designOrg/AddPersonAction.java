#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.designOrg;

import ${package}.domain.DesignOrganization;
import ${package}.domain.Person;
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
	private static final long serialVersionUID = 4455401156498542757L;
	private Long id = 0l;

    public String execute() throws Exception {

        DesignOrganization org = DesignOrganization.get(id);

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
