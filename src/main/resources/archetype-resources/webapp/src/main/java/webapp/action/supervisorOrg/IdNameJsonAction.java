#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.SupervisorOrganization;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SupervisorOrganizationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 监理单位的ID Name数据
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午3:28
 */
public class IdNameJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6414952966405547746L;
	private List<SupervisorOrganizationDto>  results;


    @Override
    public String execute() throws Exception {

        results = SupervisorOrganizationDto.idNameSupervisorOrgOf(SupervisorOrganization.findAllEnabled());

        return JSON;
    }
    public List<SupervisorOrganizationDto> getResults() {
        if (null == results) {
            return new ArrayList<SupervisorOrganizationDto>();
        }
        return results;
    }
}
