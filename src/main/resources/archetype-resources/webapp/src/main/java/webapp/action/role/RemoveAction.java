#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.role;

import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;

import ${package}.domain.Role;
import ${package}.infra.LocalSecurityRealm;
import ${package}.webapp.action.BaseAction;

/**
 * 删除角色
 * 
 * @author zjzhai
 * 
 */
public class RemoveAction extends BaseAction {

	private static final long serialVersionUID = -7791137391847987159L;

	private Long id = 0L;

	@Inject
	private LocalSecurityRealm authorizingRealm;

	public String execute() {
		Role role = Role.load(Role.class, id);
		securityApplication.removeEntity(role);
		authorizingRealm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
		return JSON;
	}

    public void setId(Long id) {
        this.id = id;
    }
}
