#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.specialty;

import ${package}.domain.Specialty;
import ${package}.pager.PageList;
import ${package}.webapp.action.BaseAction;

import java.util.List;

/**
 * User: zjzhai
 * Date: 13-3-26
 * Time: 下午6:09
 */
public class ListJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8738380501374060372L;

	private long total = 0;

    private List<Specialty> results;

    public String execute() throws Exception {
        PageList<Specialty> pageList = createPageList(Specialty.findAll());
        total = pageList.getTotal();
        results = pageList.getData();
        return JSON;
    }

    public long getTotal() {
        return total;
    }

    @org.apache.struts2.json.annotations.JSON(name="rows")
    public List<Specialty> getResults() {
        return results;
    }
}
