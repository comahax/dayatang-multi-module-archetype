#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.util.*;

/**
 * User: zjzhai
 * Date: 13-4-24
 * Time: 上午10:44
 */
public class PropertiesDto {

    private String key;

    private String value;

    public PropertiesDto(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<PropertiesDto> createBy(Properties properties) {
        List<PropertiesDto> results = new ArrayList<PropertiesDto>();

        if (null == properties) {
            return results;
        }

        Set keys = properties.keySet();

        for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            results.add(new PropertiesDto(key, properties.getProperty(key)));
        }

        return results;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
