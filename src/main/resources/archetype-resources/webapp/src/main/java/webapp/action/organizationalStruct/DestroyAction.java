#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.ErrorConstants;
import ${package}.webapp.action.BaseAction;

/**
 * 彻底删除机构
 * User: zjzhai
 * Date: 13-5-6
 * Time: 下午2:09
 */
public class DestroyAction extends BaseAction {
	private static final long serialVersionUID = -1326327552450948786L;
	/**
     * 机构ID
     */
    private Long id = 0l;

    @Override
    public String execute() throws Exception {

        if (null == id || id <= 0l) {
            return JSON;
        }

        InternalOrganization org = InternalOrganizationQuery.abilitiToAccessContainsDisabled(getGrantedScope(), id);

        try {
            commonsApplication.removeEntity(org);
        } catch (Exception e) {
            errorInfo = getText(ErrorConstants.INTERNAL_IS_REFERENCE);
        }
        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
