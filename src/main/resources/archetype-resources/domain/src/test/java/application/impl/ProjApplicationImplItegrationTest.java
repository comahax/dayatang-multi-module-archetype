#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.application.impl;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.AndCriterion;
import com.dayatang.domain.internal.EqCriterion;
import ${package}.AbstractIntegrationTest;
import ${package}.domain.OutputValue;
import com.dayatang.utils.DateUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProjApplicationImplItegrationTest extends AbstractIntegrationTest {


    @Ignore
    @Test
    public void test1() {

        QuerySettings querySettings = QuerySettings.create(OutputValue.class);
        Date from = DateUtils.date(2012, 6, 1);
        Date to = DateUtils.date(2013, 5, 31);
        List<AndCriterion> andCriterions = new ArrayList<AndCriterion>();
        while (from.before(to)) {
            andCriterions.add(new AndCriterion(new EqCriterion("monthly.year", DateUtils.getYear(from)),
                    new EqCriterion("monthly.month", DateUtils.getMonth(from))));
            from = DateUtils.dateAfter(from, 1, Calendar.MONTH);
        }
        querySettings.or(andCriterions.toArray(new AndCriterion[andCriterions.size()]));

        List<OutputValue> list = OutputValue.getRepository().find(querySettings.desc("monthly"));

        assertNotNull(list);
        assertTrue(list.size() > 0);

        for (OutputValue each : list) {
            System.out.println(each.getMonthly() + "---------------${symbol_escape}n");
        }
        //assertEquals(30,list.size());
    }

}
