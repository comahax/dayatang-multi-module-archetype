#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.ownerPerson;

import ${package}.domain.OwnerOrganization;
import ${package}.domain.Person;
import ${package}.webapp.action.person.AddBaseAction;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午2:12
 */
public class AddAction extends AddBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5893450504960147600L;
	private Long id = 0l;

    public String execute() throws Exception {
        OwnerOrganization owner = OwnerOrganization.get(id);
        if(null == owner){
            return JSON;
        }
        Person person = new Person();
        init(person);
        person.setOrganization(owner);
        commonsApplication.saveEntity(person);
        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
