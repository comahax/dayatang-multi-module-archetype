#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;


import ${package}.domain.InternalOrganization;
import ${package}.query.InternalOrganizationQuery;
import ${package}.webapp.dto.InternalOrganizationDto;
import org.apache.commons.lang3.StringUtils;

/**
 * 添加下级机构
 * User: zjzhai
 * Date: 13-5-6
 * Time: 上午12:07
 */
public class AddChildAction extends AddBaseAction {

	private static final long serialVersionUID = 8291396560738017565L;

	private Long parentId = 0l;

    /**
     * 操作成功后返回
     */
    private InternalOrganizationDto result;

    @Override
    public String execute() throws Exception {
        InternalOrganization parent = InternalOrganizationQuery.abilitiToAccess(getGrantedScope(), parentId);

        if (null == parent || StringUtils.isEmpty(name)) {
            return JSON;
        }

        InternalOrganization org = new InternalOrganization();
        init(org);
        commonsApplication.createChildInternalOrganization(org, parent);
        result = new InternalOrganizationDto(org, this);

        return JSON;
    }

    public InternalOrganizationDto getResult() {
        return result;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
