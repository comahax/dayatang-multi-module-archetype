#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.role;

import ${package}.domain.Permission;
import ${package}.domain.Role;
import ${package}.webapp.action.BaseAction;
import java.util.List;

/**
 * User: zjzhai
 * Date: 13-5-3
 * Time: 下午1:42
 */
public class AddAction extends BaseAction {

	private static final long serialVersionUID = -8620375204414872123L;

	private Long id = 0l;

    private String name;
    private String description;

    /**
     * 权限
     */
    private List<Long> perms;


    @Override
    public String execute() throws Exception {

        Role role = null;

        if (null != id && id > 0) {
            role = Role.get(id);
        }

        if (null == role) {
            role = new Role();
        }

        role.setName(name);
        role.setDescription(description);

        if (null != perms) {
            role.removeAllPermissions();
            role.grantPermissions(Permission.findById(perms).toArray(new Permission[perms.size()]));
        }

        securityApplication.saveEntity(role);

        return JSON;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPerms(List<Long> perms) {
        this.perms = perms;
    }

    @org.apache.struts2.json.annotations.JSON(serialize = false)
    public List<Long> getPerms() {
        return perms;
    }
}
