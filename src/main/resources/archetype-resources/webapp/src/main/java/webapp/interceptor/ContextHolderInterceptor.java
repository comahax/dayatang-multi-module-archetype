#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.interceptor;

import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ContextHolderInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -2033391583133640572L;

	public String intercept(ActionInvocation invocation) {
		try{
			return invocation.invoke();
		}catch(Exception ex){
			LogFactory.getLog(this.getClass()).error(ex);
		}
		return Action.ERROR;
	}

}
