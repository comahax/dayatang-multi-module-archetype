#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalOrganizationDto;

/**
 * 恢复机构
 * User: zjzhai
 * Date: 13-5-6
 * Time: 上午9:49
 */
public class ResumeAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2693109570128503266L;

	private Long id = 0l;

    private InternalOrganizationDto result;

    @Override
    public String execute() throws Exception {

        InternalOrganization org = InternalOrganizationQuery.abilitiToAccessContainsDisabled(getGrantedScope(), id);

        if (null == org) {
            return JSON;
        }

        commonsApplication.enable(org);

        result = new InternalOrganizationDto(org, this);

        return JSON;
    }

    public InternalOrganizationDto getResult() {
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
