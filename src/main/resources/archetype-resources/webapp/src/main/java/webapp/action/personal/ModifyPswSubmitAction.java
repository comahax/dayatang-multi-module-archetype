#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.personal;

import ${package}.domain.User;
import ${package}.webapp.action.BaseAction;
import org.apache.commons.lang3.StringUtils;

/**
 * User: zjzhai
 * Date: 13-4-7
 * Time: 上午9:58
 */
public class ModifyPswSubmitAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5166181459005369414L;

	private String origin;

    private String loginpass;

    private String errorInfo;

    public String execute() throws Exception {

        if(StringUtils.isEmpty(loginpass)){

            errorInfo = getText("NEW_PASSWORD_REQUIRED");

            return  JSON;
        }

        User user = getCurrentUser();

        String oldPassword = securityApplication.getPasswordEncoder().encodePassword(origin);

        if (user.getPassword().equals(oldPassword) ) {
            securityApplication.changePassword(user, loginpass);
            return JSON;
        }
        errorInfo = getText("OLD_PASSWORD_IS_ERROR");
        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }

}
