#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.dto.OutputvalueChartDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 产值概况报表1
 * User: zjzhai
 * Date: 13-5-12
 * Time: 下午4:57
 */
public class Over1QueryJsonAction extends QueryJsonAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3231023027910488072L;

	@Override
    public String execute() throws Exception {
        InternalOrganization internalOrg = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), internalScopeId);
        if (null == internalOrg) {
            internalOrg = getGrantedScope();
        }

        List<InternalOrganization> internalOrganizations = new ArrayList<InternalOrganization>(internalOrg.getImmediateChildren());
        results = OutputvalueChartDto.createOverview1ColumnChartParams(start, end, timeBucket, internalOrganizations);


        return JSON;
    }

    public OutputvalueChartDto getResults() {
        return results;
    }

}
