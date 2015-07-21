#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.SupervisorOrganization;
import ${package}.pager.PageList;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.SupervisorOrganizationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 上午10:36
 */
public class ListEnabledJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2764880630341406373L;

	private List<SupervisorOrganizationDto> results;

    private long total = 0;

    @Override
    public String execute() throws Exception {

        PageList<SupervisorOrganization> pageList = createPageList(SupervisorOrganization.findAllEnabled());

        if(null == pageList){
            return JSON;
        }
        results = SupervisorOrganizationDto.createSupervisorDtosBy(pageList.getData());

        total = pageList.getTotal();

        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<SupervisorOrganizationDto> getResults() {
        if (null == results) {
            return new ArrayList<SupervisorOrganizationDto>();
        }
        return results;
    }
}
