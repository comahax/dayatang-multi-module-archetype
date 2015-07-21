#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-3-13
 * Time: 上午10:28
 */
public class RoleDto {

    private Long id;

    private String name;

    private String description;

    private List<PermissionDto> permissions ;

    public RoleDto(Role role) {
        if (null == role) {
            return;
        }
        id = role.getId();
        name = role.getName();
        description = role.getDescription();
        if (null != role.getPermissions()) {
            permissions = PermissionDto.createBy(role.getPermissions());
        }
    }

    public static List<RoleDto> createBy(List<Role> roles) {
        List<RoleDto> results = new ArrayList<RoleDto>();

        if(null == roles){
            return results;
        }

        for(Role each : roles){
            results.add(new RoleDto(each));
        }
        return results;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }


}
