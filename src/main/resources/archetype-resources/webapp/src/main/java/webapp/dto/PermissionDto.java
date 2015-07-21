#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import ${package}.domain.Permission;

import java.util.*;

/**
 * User: zjzhai
 * Date: 13-3-13
 * Time: 上午10:29
 */
public class PermissionDto {
    private Long id;

    private String name;

    private String description;

    private int sortOrder;

    private long parentId;


    public PermissionDto(Permission permission) {
        if (null == permission) {
            return;
        }
        id = permission.getId();
        name = permission.getName();
        description = permission.getDescription();
        sortOrder = permission.getSortOrder();
        if (permission.getParent() != null) {
            parentId = permission.getParent().getId();
        } else {
            parentId = 0l;
        }
    }

    /**
     * 一次性取出所有的权限的dto
     *
     * @return
     */
    public static List<PermissionDto> allPermissions() {
        return createBy(Permission.findAll(Permission.class));
    }


    public static List<PermissionDto> createBy(Collection<Permission> permissions) {
        List<PermissionDto> results = new ArrayList<PermissionDto>();
        if (null == permissions) {
            return results;
        }
        for (Permission each : permissions) {
            results.add(new PermissionDto(each));
        }
        return results;
    }

    public long getParentId() {
        return parentId;
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

    public int getSortOrder() {
        return sortOrder;
    }
}
