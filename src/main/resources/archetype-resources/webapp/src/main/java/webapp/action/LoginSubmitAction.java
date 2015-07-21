#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action;

import java.util.Date;

import ${package}.commons.SystemVariablesUtils;
import ${package}.context.ContextHolder;
import ${package}.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ParentPackage("web")
//@InterceptorRef("captchaValidation")
@Results(value = {
        @Result(name = "success", type = "redirect", location = "worktable.action"),
        @Result(name = "input", location = "/page/login.jsp"),
        @Result(name = "verify", location = "/page/login.jsp")})
public class LoginSubmitAction extends BaseAction {

    private static final long serialVersionUID = -4112473965713011624L;

    private static Logger LOGGER = LoggerFactory.getLogger(LoginSubmitAction.class);

    private String username, password;

    public String execute() {

        User user = User.getByEmailOrUsernameOrMobile(getUsername());
        if (null == user) {
            addActionError("用户名或者密码错误");
            return INPUT;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), getPassword());

        try {
            SecurityUtils.getSubject().login(token);
            if (null == getAssignment() || getCurrentPerson().isDisabled()) {
                addActionError("用户已失效或未添加角色，请联系管理员!");
                getSession().put(ROLE_ASSIGNMENT_KEY, getAssignment().getId());
                return INPUT;
            }
            proccessLoginSuccess();
            return SUCCESS;
        } catch (AuthenticationException e) {
            LOGGER.debug("Error authenticating.", e);
            addActionError("用户名或者密码错误");
            return INPUT;
        }
    }

    private void proccessLoginSuccess() {
        getSession().put(ROLE_ASSIGNMENT_KEY, getAssignment().getId());
        // 记录用户的登录
       // getSession().remove(LOGIN_ERROR_SESSION_KEY);
        //将用户名写入线程变量，供其他模块使用
        ContextHolder.setUsername(username);
        sendNotice();
    }


    private void sendNotice() {
        if (StringUtils.isEmpty(getCurrentUserEmail())) {
            return;
        }
        noticeApplication.notice(getCurrentUserEmail(), "登录提示--" + SystemVariablesUtils.getSysnoticeTitle(), getLoginSysTipContent());
    }

    /**
     * 用户登录系统提示文字
     *
     * @return
     */
    private String getLoginSysTipContent() {
        return "CPMS系统提示：您的用户名：" + getCurrentUsername() + "在" + new Date() + "时登录。";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
