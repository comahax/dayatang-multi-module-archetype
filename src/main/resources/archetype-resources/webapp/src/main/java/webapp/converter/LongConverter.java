#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.converter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class LongConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		if (values.length == 1) {
			return toLong(values[0]);
		}
		Long[] results = new Long[values.length];
		for (int i = 0; i < values.length; i++) {
			results[i] = toLong(values[i]);
		}
		return results;
	}

	private Long toLong(String value) {
		return StringUtils.isBlank(value) ? null : Long.parseLong(value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object value) {
		if (value == null) {
			return "";
		}
        if (value instanceof Long) {
            return value.toString();
        }
        if (value.getClass().isArray()) {
        	return StringUtils.join(value, ", ");
        }
        return "";
	}

}
