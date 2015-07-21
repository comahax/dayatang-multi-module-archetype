#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.Person;
import ${package}.domain.SupervisorOrganization;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PersonDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午3:50
 */
public class IdNamePeopleJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3790716102465966860L;

	/**
     * 机构的id
     */
    private Long id = 0l;

    private List<PersonDto> results;

    @Override
    public String execute() throws Exception {

        SupervisorOrganization org = SupervisorOrganization.get(id);

        results = PersonDto.idNameOrgOf(Person.findByOrganization(org));

        return JSON;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PersonDto> getResults() {
        if (null == results) {
            return new ArrayList<PersonDto>();
        }
        return results;
    }

}
