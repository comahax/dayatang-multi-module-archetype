#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.owner;

import ${package}.domain.OwnerOrganization;
import ${package}.domain.Person;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PersonDto;

import java.util.List;

/**
 * 业主单位下人员的基本ID name
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午3:38
 */
public class IdNamePeopleJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6819305829860562391L;

	/**
     * 机构的id
     */
    private Long id = 0l;

    private List<PersonDto> results;

    @Override
    public String execute() throws Exception {

        OwnerOrganization owner = OwnerOrganization.get(id);

        results = PersonDto.idNameOrgOf(Person.findByOrganization(owner));

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PersonDto> getResults() {
        return results;
    }
}
