#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.organizationalStruct;

import ${package}.domain.InternalOrganization;
import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.InternalOrganizationDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 得到子机构列表
 * User: zjzhai
 * Date: 13-7-30
 * Time: 下午4:20
 */
public class ChildrenOfAction extends BaseAction {


	private static final long serialVersionUID = 1900676094986382691L;

	/**
     * 父机构的ID
     */
    private Long parentId = 0l;

    private InternalOrganizationDto parent;

    private List<InternalOrganizationDto> children;


    @Override
    public String execute() throws Exception {

        if (null == parentId || parentId == 0l) {
            parentId = InternalOrganization.HEADQUARTERS_ID;
        }

        InternalOrganization org = InternalOrganization.get(parentId);

        if (null == org) {
            children = new ArrayList<InternalOrganizationDto>();
            return JSON;
        }

        children = InternalOrganizationDto.createBy(org.getImmediateChildren(), this);
        parent = new InternalOrganizationDto(org);
        return JSON;
    }


    @org.apache.struts2.json.annotations.JSON(name = "results")
    public List<InternalOrganizationDto> getChildren() {
        if (null == children) {
            return new ArrayList<InternalOrganizationDto>();
        }
        return children;
    }

    public InternalOrganizationDto getParent() {
        return parent;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
