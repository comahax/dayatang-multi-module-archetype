#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.cooperationOrg;

import ${package}.domain.CooperationOrganization;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.OrganizationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-8
 * Time: 下午5:20
 */
public class ListNameIdJsonEnabledAction extends BaseAction {

	private static final long serialVersionUID = 8492436109631273863L;
	private List<OrganizationDto> results = new ArrayList<OrganizationDto>();

    @Override
    public String execute() throws Exception {
        results = OrganizationDto.idNameOf(CooperationOrganization.findAllEnabled());
        return JSON;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<OrganizationDto> getResults() {
        if (null == results) {
            return new ArrayList<OrganizationDto>();
        }
        return results;
    }
}
