#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalOrganizationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责单位机构的列表
 * User: zjzhai
 * Date: 13-4-9
 * Time: 上午10:34
 */
public class WithinScopeJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1726056330789769310L;

	private long parentId = 0l;

    private List<InternalOrganizationDto> results = new ArrayList<InternalOrganizationDto>();

    public String execute() throws Exception {
        // 待重构 TODO
        InternalOrganization org = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), parentId);
        if (org != null) {
            results.addAll(InternalOrganizationDto.createBy(org.getImmediateChildren(), this));
        } else {
            results.add(new InternalOrganizationDto(getGrantedScope(), this));
        }

        return JSON;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public List<InternalOrganizationDto> getResults() {
        return results;
    }
}
