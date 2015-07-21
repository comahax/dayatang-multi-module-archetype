#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.domain.Dictionary;
import ${package}.domain.DictionaryCategory;

public class DictionaryDisplayTag extends TagSupport {
	
	private static final long serialVersionUID = 831324399952805374L;

	private String category;
	
	private String serialNumber;

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(Dictionary.getMap(DictionaryCategory.valueOf(category)).get(serialNumber));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}
}
