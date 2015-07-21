#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;

import ${package}.webapp.dto.OutputvalueChartDto;

/**
 * 客户分类产值报表
 * User: zjzhai
 * Date: 13-5-12
 * Time: 下午6:19
 */
public class CustomColumnQueryJsonAction extends QueryJsonAction {

	private static final long serialVersionUID = 5149623982705787099L;

	@Override
    public String execute() throws Exception {
        results = OutputvalueChartDto.createCustomColumnChartParams(start, end, timeBucket, getGrantedScope());
        return JSON;
    }

    public OutputvalueChartDto getResults() {
        return results;
    }
}
