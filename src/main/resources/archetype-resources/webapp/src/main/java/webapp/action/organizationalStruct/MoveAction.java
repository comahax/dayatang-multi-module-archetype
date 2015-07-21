#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 机构的调动
 * User: zjzhai
 * Date: 13-7-30
 * Time: 下午2:28
 */
public class MoveAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3305014743028662377L;

	/**
     * 父机构的ID
     */
    private Long parentId = 0l;

    /**
     * 调动机构的ID
     */
    private Long id = 0l;


    @Override
    public String execute() throws Exception {

        if (null == parentId || parentId <= 0 || null == id || id <= 0) {
            return JSON;
        }

        InternalOrganization parent = InternalOrganization.get(parentId);

        if (null == parent) {
            errorInfo = "找不到父机构!";
            return JSON;
        }

        InternalOrganization child = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), id);

        if (null == child) {
            errorInfo = "找不到该机构！";
            return JSON;
        }

        commonsApplication.createChildInternalOrganization(child, parent);

        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
