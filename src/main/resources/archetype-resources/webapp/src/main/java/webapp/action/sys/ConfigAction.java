#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.sys;

import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.PropertiesDto;

import java.util.List;
import java.util.Properties;

/**
 * User: zjzhai
 * Date: 13-4-24
 * Time: 上午10:27
 */
public class ConfigAction extends BaseAction{

	private static final long serialVersionUID = -8148472680162100371L;
	private List<PropertiesDto> results;

    @Override
    public String execute() throws Exception {

        Properties properties = getConfiguration().getProperties();

        results = PropertiesDto.createBy(properties);

        return SUCCESS;
    }

    public List<PropertiesDto> getResults() {
        return results;
    }
}
