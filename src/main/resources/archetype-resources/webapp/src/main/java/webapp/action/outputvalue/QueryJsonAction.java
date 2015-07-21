#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.outputvalue;


import ${package}.webapp.commons.TimeBucket;

import ${package}.webapp.action.BaseAction;
import ${package}.webapp.dto.OutputvalueChartDto;


import java.util.Date;

/**
 * 查询产值
 * User: zjzhai
 * Date: 13-4-25
 * Time: 下午3:32
 */
public class QueryJsonAction extends BaseAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1716493443720173518L;
	//年份范围
    protected Date start;
    protected Date end;

    //时间段
    protected TimeBucket timeBucket = TimeBucket.YEAR;

    //机构范围
    protected Long internalScopeId = 0l;

    //项目范围
    protected Long projectId = 0l;

    protected OutputvalueChartDto results;

    public void setTimeBucket(TimeBucket timeBucket) {
        this.timeBucket = timeBucket;
    }


    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setInternalScopeId(Long internalScopeId) {
        this.internalScopeId = internalScopeId;
    }
}
