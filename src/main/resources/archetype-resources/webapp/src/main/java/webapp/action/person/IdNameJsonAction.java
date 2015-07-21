#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.person;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Person;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PersonDto;

import java.util.List;

/**
 * 内部机构的人员管理
 *
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午2:44
 */
public class IdNameJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5767265383760239905L;

	private Long orgId = 0l;

    private List<PersonDto> results;

    @Override
    public String execute() throws Exception {
        results = PersonDto.idNameOrgOf(Person.findByOrganization(InternalOrganization.get(orgId)));
        return JSON;
    }

    public List<PersonDto> getResults() {
        return results;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
