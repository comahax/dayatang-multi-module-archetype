#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.CooperationOrganization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-4-19
 * Time: 上午10:53
 */
public class CooperationOrganizationDto extends OrganizationDto {


    public CooperationOrganizationDto(CooperationOrganization organization) {
        super(organization);
    }


    public static List<CooperationOrganizationDto> cooperationOrganizationDtoList(Collection<CooperationOrganization> orgs){
        List<CooperationOrganizationDto> results = new ArrayList<CooperationOrganizationDto>();

        for(CooperationOrganization each : orgs){
            CooperationOrganizationDto dto = new CooperationOrganizationDto(each);
            results.add(dto);
        }
        return results;
    }
}
