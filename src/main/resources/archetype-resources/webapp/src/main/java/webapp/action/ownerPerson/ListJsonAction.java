#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.ownerPerson;

import ${package}.domain.OwnerOrganization;
import ${package}.domain.Person;
import ${package}.pager.PageList;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PersonDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午1:06
 */
public class ListJsonAction extends BaseAction {

	private static final long serialVersionUID = 5966445446631290532L;

	private Long ownerId = 0l;

    private long total = 0l;

    private List<PersonDto> results;

    @Override
    public String execute() throws Exception {
        OwnerOrganization owner = OwnerOrganization.get(ownerId);
        if (null == owner) {
            return JSON;
        }
        PageList<Person> pageList = createPageList(Person.findByOrganization(owner));
        if (null == pageList) {
            return JSON;
        }
        results = PersonDto.createBy(pageList.getData());
        total = pageList.getTotal();
        return JSON;
    }

    public long getTotal() {
        return total;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<PersonDto> getResults() {
        if (null == results) {
            return new ArrayList<PersonDto>();
        }
        return results;
    }
}
