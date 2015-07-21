#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.owner;

import ${package}.domain.OwnerOrganization;
import ${package}.pager.PageList;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.OwnerOrganizationDto;

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
	private static final long serialVersionUID = -8898456399343329949L;

	private List<OwnerOrganizationDto> results;

    private long total = 0;

    @Override
    public String execute() throws Exception {

        PageList<OwnerOrganization> pageList = createPageList(OwnerOrganization.findAll(OwnerOrganization.class));

        if(null == pageList){
            return JSON;
        }
        results = OwnerOrganizationDto.createByOwner(pageList.getData());

        total = pageList.getTotal();

        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<OwnerOrganizationDto> getResults() {
        return results;
    }
}
