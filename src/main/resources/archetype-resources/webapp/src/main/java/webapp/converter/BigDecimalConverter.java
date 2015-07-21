#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.converter;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class BigDecimalConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		if (values.length == 1) {
			return toBigDecimal(values[0]);
		}
		
		BigDecimal[] results = new BigDecimal[values.length];
		for (int i = 0; i < values.length; i++) {
			results[i] = toBigDecimal(values[i]);
		}
		return results;
	}

	private BigDecimal toBigDecimal(String stringVal) {
		if (StringUtils.isBlank(stringVal)) {
			return null;
		}
		return new BigDecimal(stringVal);
	}

	@SuppressWarnings("rawtypes")
	public String convertToString(Map context, Object o) {
		if (o instanceof BigDecimal) {
			return o.toString();
		}
		return "";
	}
}
