#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;


import org.apache.struts2.convention.annotation.Result;

/**
 * 找回密码页面
 * User: zjzhai
 * Date: 13-4-9
 * Time: 下午4:14
 */
@Result(name = "success", location = "/page/send-password-forgot.jsp")
public class PasswordForgotAction extends BaseAction {
	private static final long serialVersionUID = -1090249930394419317L;

	@Override
    public String execute() throws Exception {
        return SUCCESS;
    }


}
