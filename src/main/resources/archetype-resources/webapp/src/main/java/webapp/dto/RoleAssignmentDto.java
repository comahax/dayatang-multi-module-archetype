#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.RoleAssignment;
import ${package}.webapp.action.BaseAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-3-13
 * Time: 下午2:19
 */
public class RoleAssignmentDto {

    private Long id;

    private UserDto user;

    private RoleDto role;

    private InternalOrganizationDto organization;

    public static List<RoleAssignmentDto> createBy(Collection<RoleAssignment> assignments, BaseAction action) {
        List<RoleAssignmentDto> results = new ArrayList<RoleAssignmentDto>();

        for (RoleAssignment each : assignments) {
            results.add(new RoleAssignmentDto(each, action));
        }

        return results;
    }

    public RoleAssignmentDto(RoleAssignment roleAssignment, BaseAction action) {
        if (null == roleAssignment) {
            return;
        }
        id =roleAssignment.getId();
        user = new UserDto(roleAssignment.getUser());
        role = new RoleDto(roleAssignment.getRole());
        organization = new InternalOrganizationDto(roleAssignment.getOrganization(), action);
    }

    public Long getId() {
        return id;
    }

    public UserDto getUser() {
        return user;
    }

    public RoleDto getRole() {
        return role;
    }

    public InternalOrganizationDto getOrganization() {
        return organization;
    }
}
