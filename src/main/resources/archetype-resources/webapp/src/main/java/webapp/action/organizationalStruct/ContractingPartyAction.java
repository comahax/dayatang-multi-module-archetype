#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalOrganizationDto;

import java.util.List;

/**
 * 返回可做为开展项目的公司的列表
 * User: zjzhai
 * Date: 13-6-4
 * Time: 下午9:00
 */
public class ContractingPartyAction extends BaseAction {

	private static final long serialVersionUID = 6008027514872545630L;
	private List<InternalOrganizationDto> results;

    @Override
    public String execute() throws Exception {
        results = InternalOrganizationDto.createBy(InternalOrganizationQuery.create().immediateChildrenOf(InternalOrganization.headquarter()).list(), this);
        results.add(new InternalOrganizationDto(InternalOrganization.headquarter(), this));
        return JSON;
    }

    public List<InternalOrganizationDto> getResults() {
        return results;
    }
}
