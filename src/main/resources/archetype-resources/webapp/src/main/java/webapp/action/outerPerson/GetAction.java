#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outerPerson;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Person;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PersonDto;

/**
 * 拿到某个外部人员
 * User: zjzhai
 * Date: 13-3-21
 * Time: 下午4:33
 */
public class GetAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

    private PersonDto result;

    @Override
    public String execute() throws Exception {

        Person person = PersonQuery.create().id(id).getSingleResult();

        //不能访问内部机构的人员
        if (person.getOrganization() instanceof InternalOrganization) {
            return NOT_FOUND;
        }

        result = new PersonDto(person);

        return JSON;
    }

    public PersonDto getResult() {
        return result;
    }

    public void setId(long id) {
        this.id = id;
    }
}
