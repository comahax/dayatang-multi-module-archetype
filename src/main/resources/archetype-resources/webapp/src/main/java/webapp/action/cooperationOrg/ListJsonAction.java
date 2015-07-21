#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.cooperationOrg;

import ${package}.domain.CooperationOrganization;
import ${package}.pager.PageList;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.CooperationOrganizationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 上午10:36
 */
public class ListJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3448419056329113968L;

	private List<CooperationOrganizationDto> results;

    private long total = 0;

    @Override
    public String execute() throws Exception {

        PageList<CooperationOrganization> pageList = createPageList(CooperationOrganization.findAll(CooperationOrganization.class));

        if(null == pageList){
            return JSON;
        }
        results = CooperationOrganizationDto.cooperationOrganizationDtoList(pageList.getData());

        total = pageList.getTotal();

        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<CooperationOrganizationDto> getResults() {
        if (null == results) {
            results = new ArrayList<CooperationOrganizationDto>();
        }
        return results;
    }
}
