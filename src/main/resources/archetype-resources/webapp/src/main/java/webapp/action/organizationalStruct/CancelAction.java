#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.ErrorConstants;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalOrganizationDto;

/**
 * 撤消某机构
 * User: zjzhai
 * Date: 13-4-7
 * Time: 下午7:11
 */
public class CancelAction extends BaseAction {

	private static final long serialVersionUID = -1802802802615357850L;

	private Long id = 0l;

    private String errorInfo;

    private InternalOrganizationDto result;

    @Override
    public String execute() throws Exception {

        //不能撤消总公司
        if (InternalOrganization.HEADQUARTERS_ID == id) {
            errorInfo = getText("THE_CORPORATION_DOES_NOT_ALLOW_UNDO");
            return JSON;
        }

        InternalOrganization org = InternalOrganizationQuery.abilitiToAccessContainsDisabled(getGrantedScope(), id);

        if (null == org) {
            errorInfo = getText(ErrorConstants.RESOUCES_NOT_FOUND);
            return JSON;
        }


        commonsApplication.disable(org);

        result = new InternalOrganizationDto(org, this);

        return JSON;
    }

    public InternalOrganizationDto getResult() {
        return result;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
