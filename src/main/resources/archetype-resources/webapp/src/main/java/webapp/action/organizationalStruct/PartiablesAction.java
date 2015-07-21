#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalOrganizationDto;

import java.util.List;

/**
 * 可做为合同方的机构
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午2:25
 */
public class PartiablesAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2725854319449956101L;
	private List<InternalOrganizationDto> results;


    @Override
    public String execute() throws Exception {

        results = InternalOrganizationDto.createBy(InternalOrganization.getAllAbilityToBeParty(), this);


        return JSON;
    }

    public List<InternalOrganizationDto> getResults() {
        return results;
    }
}
