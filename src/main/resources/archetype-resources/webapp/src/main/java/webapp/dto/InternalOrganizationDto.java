#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ${package}.domain.InternalOrganization;
import ${package}.webapp.action.BaseAction;

/**
 * 内部机构DTO
 *
 * @author zjzhai
 */
public class InternalOrganizationDto extends OrganizationDto {

    public InternalOrganizationDto() {
    }

    /**
     * 机构的分类
     */
    private String internalCategory;

    private String fullName;

    public InternalOrganizationDto(InternalOrganization internal, BaseAction action) {
        super(internal);
        if (null != internal.getInternalCategory()) {
            internalCategory = action.getText(internal.getInternalCategory().toString());
        }
        fullName = internal.getFullName();
    }

    public InternalOrganizationDto(InternalOrganization internal) {
        super(internal);
        internalCategory = internal.getInternalCategory() == null ? null : internal.getInternalCategory().toString();
        fullName = internal.getFullName();
    }

    public static List<InternalOrganizationDto> createBy(Collection<InternalOrganization> internals, BaseAction action) {
        List<InternalOrganizationDto> results = new ArrayList<InternalOrganizationDto>();

        if (null == internals) {
            return results;
        }

        for (InternalOrganization each : internals) {
            results.add(new InternalOrganizationDto(each, action));
        }

        return results;
    }

    public String getInternalCategory() {
        return internalCategory;
    }

    public String getFullName() {
        return fullName;
    }

}
