#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Organization;
import ${package}.domain.SupervisorOrganization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-18
 * Time: 下午3:29
 */
public class SupervisorOrganizationDto extends OrganizationDto {

    public SupervisorOrganizationDto(SupervisorOrganization org) {
        super(org);
    }
    public SupervisorOrganizationDto(Organization org) {
        super(org);
    }

    public SupervisorOrganizationDto() {

    }


    public static List<SupervisorOrganizationDto> createSupervisorDtosBy(Collection<SupervisorOrganization> organizations) {
        List<SupervisorOrganizationDto> results = new ArrayList<SupervisorOrganizationDto>();
        for (SupervisorOrganization each : SupervisorOrganization.findAllEnabled()) {
            SupervisorOrganizationDto dto = new SupervisorOrganizationDto(each);
            results.add(dto);
        }
        return results;
    }


    public static List<SupervisorOrganizationDto> idNameSupervisorOrgOf(Collection<SupervisorOrganization> organizations) {
        List<SupervisorOrganizationDto> results = new ArrayList<SupervisorOrganizationDto>();
        for (SupervisorOrganization each : SupervisorOrganization.findAllEnabled()) {
            SupervisorOrganizationDto dto = new SupervisorOrganizationDto();
            dto.id = each.getId();
            dto.name = each.getName();
            results.add(dto);
        }
        return results;
    }

}