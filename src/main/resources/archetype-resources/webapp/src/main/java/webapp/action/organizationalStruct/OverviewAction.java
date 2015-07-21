#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalOrgOverviewDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构的概况
 * User: zjzhai
 * Date: 13-5-7
 * Time: 上午3:52
 */
public class OverviewAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1674621422532367251L;

	/**
     * 机构的ID
     */
    private long id = 0l;

    private List<InternalOrgOverviewDto> results;

    private int total;

    @Override
    public String execute() throws Exception {

        if (id <= 0l) {
            return JSON;
        }

        InternalOrganization org = InternalOrganizationQuery.abilitiToAccessContainsDisabled(getGrantedScope(), id);

        if (null == org) {
            return JSON;
        }

        results = InternalOrgOverviewDto.createBy(org.getAllImmediateChildren(), this);
        total = results.size();

        return JSON;
    }

    public int getTotal() {
        return total;
    }

    public void setId(long id) {
        this.id = id;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<InternalOrgOverviewDto> getResults() {
        if (null == results) {
            return new ArrayList<InternalOrgOverviewDto>();
        }
        return results;
    }
}
