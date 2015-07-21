#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.designOrg;

import ${package}.domain.DesignOrganization;
import ${package}.pager.PageList;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.DesignOrganizationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 上午10:36
 */
public class ListEnabledJsonAction extends BaseAction {

	private static final long serialVersionUID = -7722143528239474868L;

	private List<DesignOrganizationDto> results;

    private long total = 0;

    @Override
    public String execute() throws Exception {

        PageList<DesignOrganization> pageList = createPageList(DesignOrganization.findAllEnabled());

        if(null == pageList){
            return JSON;
        }
        results = DesignOrganizationDto.designOrganizationDtoList(pageList.getData());

        total = pageList.getTotal();

        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<DesignOrganizationDto> getResults() {
        if (null == results) {
            return new ArrayList<DesignOrganizationDto>();
        }
        return results;
    }
}
