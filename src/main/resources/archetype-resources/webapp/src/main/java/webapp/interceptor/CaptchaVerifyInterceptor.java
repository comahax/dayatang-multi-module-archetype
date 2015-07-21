#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.interceptor;

import java.util.Map;

import ${package}.webapp.ErrorConstants;

import com.dayatang.configuration.Configuration;
import com.dayatang.domain.InstanceFactory;
import com.google.code.jcaptcha4struts2.core.PluginConstants;
import com.google.code.jcaptcha4struts2.core.interceptors.JCaptchaValidationIntercepter;
import com.google.code.jcaptcha4struts2.core.validation.JCaptchaValidator;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;

/**
 * 前台检验器
 */

public class CaptchaVerifyInterceptor extends JCaptchaValidationIntercepter {

    private static final long serialVersionUID = -4137361906512418091L;
    /**
     * 用户登录尝试密码的次数在session中key
     */
    private static final String LOGIN_ERROR_SESSION_KEY = "wrongCount";

    /**
     * 实际错误次数
     */
    private long wrongCount = 0l;

    private Configuration configuration;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = ActionContext.getContext().getSession();
        if (session.get(LOGIN_ERROR_SESSION_KEY) == null) {
            session.put(LOGIN_ERROR_SESSION_KEY, 0l);
        }
        wrongCount = (Long) session.get(LOGIN_ERROR_SESSION_KEY);
        wrongCount++;
        session.put(LOGIN_ERROR_SESSION_KEY, wrongCount);
        if (wrongCount <= getConfiguration().getLong("login.error.limit")) {
            return invocation.invoke();
        }
        try{
            if (!JCaptchaValidator.validate()) {
                ValidationAware action = (ValidationAware) invocation.getAction();

                action.addFieldError(PluginConstants.J_CAPTCHA_RESPONSE, getValidationErrorMessage());
                return "verify";
            }
        } catch (Exception e){
            ValidationAware action = (ValidationAware) invocation.getAction();

            action.addFieldError(PluginConstants.J_CAPTCHA_RESPONSE, getValidationErrorMessage());
            return "verify";
        }


        return invocation.invoke();
    }

    @Override
    protected String getValidationErrorMessage() {
        return ErrorConstants.CAPTCHA_VERIFY_ERROR;
    }

    public Configuration getConfiguration() {
        if (configuration == null) {
            configuration = InstanceFactory.getInstance(Configuration.class, "AppConfig");
        }
        return configuration;
    }

}
