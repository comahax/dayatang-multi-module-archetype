#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import ${package}.domain.PasswordForgot;
import ${package}.domain.User;
import ${package}.webapp.ErrorConstants;
import org.apache.commons.lang3.StringUtils;

import static ${package}.commons.SystemVariablesUtils.getSysnoticeTitle;

/**
 * User: zjzhai
 * Date: 13-4-9
 * Time: 下午4:43
 */

public class SendPasswordForgotAction extends BaseAction {


	private static final long serialVersionUID = -3845995117659899007L;

	private static final String GET_PASSWORD_ACTION_URL = "get-back-password.action";

    private String email;

    public String execute() throws Exception {

        if (StringUtils.isEmpty(email)) {
            errorInfo = getText(ErrorConstants.THE_EMAIL_IS_REQUIRED);
            return JSON;
        }

        User user = User.getByEmailOrUsernameOrMobile(email);
        if (null == user) {
            errorInfo = getText(ErrorConstants.THE_EMAIL_IS_NOT_EXIST);
            return SUCCESS;
        }
        PasswordForgot forgot = securityApplication.passwordForgot(user, email);

        emailNoticeResetPassword(user, forgot);

        return SUCCESS;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    protected final void emailNoticeResetPassword(User user, PasswordForgot forgot) {
        noticeApplication.notice(user.getEmail(), "重置密码－－" + getSysnoticeTitle(), resetPasswordEmailContent(forgot));
    }

    private String resetPasswordEmailContent(PasswordForgot forgot) {
        return new StringBuilder().append("请点击重置密码链接：")
                .append(basePath)
                .append(GET_PASSWORD_ACTION_URL + "?")
                .append("symbol=" + forgot.getUserSymbol()).append("&")
                .append("checksum=" + forgot.getChecksum())
                .toString();
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
