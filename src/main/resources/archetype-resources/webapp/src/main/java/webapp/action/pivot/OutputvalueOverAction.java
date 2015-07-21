#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.pivot;

import ${package}.webapp.commons.TimeBucket;
import ${package}.webapp.action.BaseAction;
import com.dayatang.utils.DateUtils;

import java.util.Date;

/**
 * User: zjzhai
 * Date: 13-4-26
 * Time: 下午12:51
 */
public class OutputvalueOverAction extends BaseAction {

	private static final long serialVersionUID = 1532476383331151119L;

	public TimeBucket getYear() {
        return TimeBucket.YEAR;
    }

    public TimeBucket getMonth() {
        return TimeBucket.MONTH;
    }

    public TimeBucket getQuarter() {
        return TimeBucket.QUARTER;
    }

    public int getCurrentYear(){
        return DateUtils.getYear(new Date());
    }


}
