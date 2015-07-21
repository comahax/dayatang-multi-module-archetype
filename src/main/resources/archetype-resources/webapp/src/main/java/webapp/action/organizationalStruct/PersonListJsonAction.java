#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.domain.Person;
import ${package}.pager.PageList;
import ${package}.query.InternalOrganizationQuery;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalPersonDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午4:31
 */
public class PersonListJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2246998538910163138L;

	private long orgId = 0l;

    private List<InternalPersonDto> results;

    private Long total = 0l;

    private String name;


    @Override
    public String execute() throws Exception {


        InternalOrganization org = InternalOrganizationQuery.abilitiToAccessContainsDisabled(getGrantedScope(), orgId);

        if (null == org) {
            org = getGrantedScope();
        }


        PersonQuery query = PersonQuery.create().subordinateWithSelf(org).excludeId(getSystemAdminPersonId());
        if (StringUtils.isNotEmpty(name)) {
            name = StringUtils.trim(name);
            query.nameLike(name);
        }

        PageList<Person> pageList = createPageList(query);

        if (null == pageList) {
            return JSON;
        }

        results = InternalPersonDto.createBy(pageList.getData(), this);

        total = pageList.getTotal();

        return JSON;
    }


    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }


    @org.apache.struts2.json.annotations.JSON(name = "rows")
    public List<InternalPersonDto> getResults() {
        if (null == results) {
            results = new ArrayList<InternalPersonDto>();
        }
        return results;
    }

    public Long getTotal() {
        return total;
    }

    public void setName(String name) {
        this.name = name;
    }
}

