#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.owner;

import java.util.List;

import ${package}.domain.OwnerOrganization;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.OwnerOrganizationDto;

/**
 * 
 * @author zjzhai
 * 
 */
public class ListNameJsonAction extends BaseAction {
	private static final long serialVersionUID = 5769582264186090562L;

	private List<OwnerOrganizationDto> results;

	public String execute() throws Exception {

		results = OwnerOrganizationDto.idNameOfOwners(OwnerOrganization.findAllOwnerEnabled());

		return "json";
	}

	public List<OwnerOrganizationDto> getResults() {
		return results;
	}


}
