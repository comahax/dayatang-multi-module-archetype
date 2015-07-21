#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;

import ${package}.webapp.dto.OutputvalueChartDto;

/**
 * 项目类型的分类的饼状图的JSON数据
 *
 * User: zjzhai
 * Date: 13-5-14
 * Time: 下午4:13
 */
public class ProjecttypePieQueryJsonAction extends QueryJsonAction{


    /**
	 * 
	 */
	private static final long serialVersionUID = 5663940960271394713L;

	@Override
    public String execute() throws Exception {
        results = OutputvalueChartDto.createProjecttypePieChartParams(start, end, timeBucket, getGrantedScope());
        return JSON;
    }

    public OutputvalueChartDto getResults() {
        return results;
    }



}
