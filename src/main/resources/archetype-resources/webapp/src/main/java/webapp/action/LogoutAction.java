#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import org.apache.shiro.SecurityUtils;

public class LogoutAction extends BaseAction {

	private static final long serialVersionUID = -8067819482107241841L;

	public String execute() {
		SecurityUtils.getSubject().logout();
		return WORKTABLE;
	}

}
