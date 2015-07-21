#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Dictionary;
import ${package}.domain.OwnerOrganization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 业主单位
 *
 * @author zjzhai
 */
public class OwnerOrganizationDto extends OrganizationDto {

    private String ownerCategory;

    public OwnerOrganizationDto() {
    }

    private OwnerOrganizationDto(OwnerOrganization owner) {
        super(owner);
        ownerCategory = Dictionary.getDictionaryTextBySerialNumBer(owner.getOwnerCategory());
    }

    public static OwnerOrganizationDto idNameOfOwners(OwnerOrganization owner) {
        OwnerOrganizationDto result = new OwnerOrganizationDto();
        if (null == owner) {
            return result;
        }
        result.id = owner.getId();
        result.name = owner.getName();
        result.ownerCategory = owner.getOwnerCategory();
        return result;
    }

    public static List<OwnerOrganizationDto> idNameOfOwners(Collection<OwnerOrganization> owners) {
        List<OwnerOrganizationDto> results = new ArrayList<OwnerOrganizationDto>();
        if (null == owners) {
            return results;
        }
        for (OwnerOrganization each : owners) {
            results.add(idNameOfOwners(each));
        }
        return results;
    }

    public static List<OwnerOrganizationDto> createByOwner(Collection<OwnerOrganization> owners) {
        List<OwnerOrganizationDto> results = new ArrayList<OwnerOrganizationDto>();

        if (null == owners) {
            return results;
        }

        for (OwnerOrganization each : owners) {
            results.add(new OwnerOrganizationDto(each));
        }
        return results;
    }

    public String getOwnerCategory() {
        return ownerCategory;
    }

}
