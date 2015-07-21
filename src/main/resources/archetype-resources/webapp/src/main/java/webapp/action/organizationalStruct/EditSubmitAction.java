#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.dto.InternalOrganizationDto;
import org.apache.commons.lang3.StringUtils;

/**
 * 编辑机构
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午6:38
 */
public class EditSubmitAction extends AddBaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3031660663238546969L;

	private Long id = 0l;

    private InternalOrganizationDto result;


    @Override
    public String execute() throws Exception {

        InternalOrganization org = InternalOrganizationQuery.abilitiToAccessContainsDisabled(getGrantedScope(), id);

        if (null == org || StringUtils.isEmpty(name)) {
            return NOT_FOUND;
        }

        init(org);
        commonsApplication.saveEntity(org);
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
