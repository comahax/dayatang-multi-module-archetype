#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.supervisorOrg;

import ${package}.domain.Person;
import ${package}.domain.SupervisorOrganization;
import ${package}.pager.PageList;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PersonDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午1:06
 */
public class ListPeopleEnabledJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1555930405538989539L;

	/**
     * 机构ID
     */
    private Long id = 0l;

    private long total = 0l;

    private List<PersonDto> results;

    @Override
    public String execute() throws Exception {
        SupervisorOrganization org = SupervisorOrganization.get(id);
        if(null == org){
            return JSON;
        }
        PageList<Person> pageList = createPageList(PersonQuery.create().organization(org).enabled());
        if(null == pageList){
            return JSON;
        }
        results = PersonDto.createBy(pageList.getData());
        total = pageList.getTotal();
        return JSON;
    }

    public long getTotal() {
        return total;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<PersonDto> getResults() {
        if (null == results) {
            return new ArrayList<PersonDto>();
        }
        return results;
    }
}
