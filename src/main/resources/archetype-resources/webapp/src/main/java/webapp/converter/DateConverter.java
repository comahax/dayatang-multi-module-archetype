#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ${package}.webapp.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.dayatang.configuration.WritableConfiguration;
import com.dayatang.domain.InstanceFactory;
import ${package}.Constants;
import com.opensymphony.xwork2.conversion.TypeConversionException;

/**
 * 
 * @author yyang
 *
 */
public class DateConverter extends StrutsTypeConverter {

	private WritableConfiguration configuration;
	
	private WritableConfiguration getConfiguration() {
		if (configuration == null) {
			configuration = InstanceFactory.getInstance(WritableConfiguration.class);
		}
		return configuration;
	}

	public void setConfiguration(WritableConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@SuppressWarnings("rawtypes")
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || values.length == 0) {
			return null;
		}
		if (values.length == 1) {
			return parseDate(values[0]);
		}
		Date[] results = new Date[values.length];
		for (int i = 0; i < values.length; i++) {
			results[i] = parseDate(values[i]);
		}
		return results;
    }
	
    private Date parseDate(String dateString) {
    	if (StringUtils.isBlank(dateString)) {
    		return null;
    	}
		try {
			return DateUtils.parseDate(dateString,
					getConfiguration().getString(Constants.DATE_FORMAT),
					getConfiguration().getString(Constants.DATE_TIME_FORMAT),
					getConfiguration().getString(Constants.TIME_FORMAT));
		} catch (ParseException e) {
            throw new TypeConversionException("Date String ${symbol_escape}"" + dateString + "${symbol_escape}" cannot be convert to Object of type Date.", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public String convertToString(Map context, Object value) {
		if (value == null) {
			return "";
		}
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(getConfiguration().getString(Constants.DATE_FORMAT));
            return sdf.format((Date) value);
        }
        if (value.getClass().isArray()) {
        	return StringUtils.join(value, ", ");
        }
        return "";
    }
}

