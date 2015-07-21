#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.DesignOrganization;
import ${package}.domain.Organization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-19
 * Time: 上午10:29
 */
public class DesignOrganizationDto extends OrganizationDto {
    public DesignOrganizationDto(Organization organization) {
        super(organization);
    }

    public DesignOrganizationDto(DesignOrganization organization) {
        super(organization);
    }

    public static List<DesignOrganizationDto> designOrganizationDtoList(Collection<DesignOrganization> orgs){
        List<DesignOrganizationDto> results = new ArrayList<DesignOrganizationDto>();

        for(DesignOrganization each : orgs){
            DesignOrganizationDto dto = new DesignOrganizationDto(each);
            results.add(dto);
        }

        return results;
    }

}
