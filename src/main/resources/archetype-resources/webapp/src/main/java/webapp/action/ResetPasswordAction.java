#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import ${package}.domain.PasswordForgot;
import ${package}.domain.User;
import ${package}.webapp.ErrorConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * User: Administrator
 * Date: 13-5-16
 * Time: 下午7:49
 */

public class ResetPasswordAction extends BaseAction {
	private static final long serialVersionUID = -2281180332411866289L;

	private String password;

    private String symbol;

    private String checksum;


    @Override
    public String execute() throws Exception {

        if (StringUtils.isEmpty(password)) {
            errorInfo = "密码为空!";
            return JSON;
        }

        PasswordForgot forgot = PasswordForgot.getBySymbolChecksum(symbol, checksum);

        if (null == forgot) {
            errorInfo = getText(ErrorConstants.RESET_PASSWORD_FAIL);
            return JSON;
        }

        User user = User.getByEmailOrUsernameOrMobile(forgot.getUserSymbol());

        if (null == user) {
            errorInfo = getText(ErrorConstants.RESET_PASSWORD_FAIL);
            return JSON;
        }

        securityApplication.changePassword(user, password);
        securityApplication.saveEntity(user);
        securityApplication.removeEntity(forgot);

        return JSON;
    }

    public String getErrorInfo() {
        return errorInfo;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

}
