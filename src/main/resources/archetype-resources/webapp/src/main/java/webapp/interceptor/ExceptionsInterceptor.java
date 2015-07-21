#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.interceptor;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dayatang.configuration.WritableConfiguration;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 异常拦截器，对系统异常进行拦截处理
 * 
 * @author wenxiang.Zhou
 * 
 */

public class ExceptionsInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -6446113754474131430L;



	private static final String SYS_MONITOR = "sysmonitor.email";

	private static final String DEFAULT_SYS_MONITOR = "zjzhai@dayatang.com";


	@Inject
	@Named("AppConfig")
	private static WritableConfiguration configuration;

	@Override
	public String intercept(ActionInvocation invocation) {
		try {
			String result = invocation.invoke();
			return result;
		} catch (Exception e) {
			Log log = LogFactory.getLog(invocation.getAction().getClass());
			// 默认错误均为系统错误
			ValueStack stack = invocation.getStack();

			/**
			 * 错误信息
			 */
			String logContent = "";

			/*if (e instanceof BaseRuntimeException) {
				stack.setValue(ErrorConstants.ERROR_MSG, e.toString());
				// stack.setValue(ErrorConstants.ERROR_URL, e.toString());
			} else {
				// 其他异常类
				if (e instanceof RuntimeException) {
					RuntimeException exception = new RuntimeException(e);
					logContent = RUNTIME_MSG + " : >>>>" + exception + " <<<< ${symbol_escape}n the email is sending...";
				} else {
					logContent = SYSTEM_MSG + " : >>>>  " + e + " <<<<  ${symbol_escape}n the email is sending...";
				}

				stack.setValue(ErrorConstants.ERROR_MSG, ErrorConstants.UNEXPECTED_ERROR);
				stack.setValue(ErrorConstants.ERROR_URL, null);
				log.error(logContent);
			}

			// 发送邮件通知管理员
			noticeTaskExecutor.sendNotice(getSysMonitorEmailAddress(), "CPMS系统异常", logContent);*/
		}

		return Action.ERROR;
	}

	/**
	 * 获取系统管理员的邮件地址
	 * 
	 * @return
	 */
	public static String getSysMonitorEmailAddress() {
		return getConfiguration().getString(SYS_MONITOR, DEFAULT_SYS_MONITOR);
	}

	public static WritableConfiguration getConfiguration() {
		return configuration;
	}

}
