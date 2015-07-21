#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.dayatang.domain.InstanceFactory;
import com.dayatang.spring.factory.SpringInstanceProvider;

public class InitializationListener extends ContextLoaderListener {


	
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		SpringInstanceProvider SpringInstanceProvider = new SpringInstanceProvider(getCurrentWebApplicationContext());
		InstanceFactory.setInstanceProvider(SpringInstanceProvider);
	}
	

	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}


	
}
