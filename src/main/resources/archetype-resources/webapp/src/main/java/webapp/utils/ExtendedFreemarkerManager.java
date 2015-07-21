#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.utils;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;

import ${package}.webapp.freemarkModel.ConvertYuanToTenThousand;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Created with IntelliJ IDEA. User: zjzhai Date: 13-8-14 Time: 上午11:26
 */
public class ExtendedFreemarkerManager extends FreemarkerManager {

	@Override
	protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException {
		Configuration configuration = super.createConfiguration(servletContext);
		configuration.setSharedVariable("convertYuanToTenThousand", new ConvertYuanToTenThousand());
		return configuration;
	}

}
