#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import ${package}.domain.OutputValue;
import ${package}.domain.OutputValueReporter;
import ${package}.domain.Person;

import java.util.Date;

public class OutputValueReporterQuery extends BaseQuery<OutputValueReporter> {

	public OutputValueReporterQuery() {
		super(QuerySettings.create(OutputValueReporter.class));
	}

    public OutputValueReporterQuery outputvalue(OutputValue outputValue){
        querySettings.eq("outputValue", outputValue);
        return this;
    }

	public OutputValueReporterQuery reporter(Person reporter) {
		querySettings.eq("reporter", reporter);
		return this;
	}

	public OutputValueReporterQuery between(Date from, Date to) {
		if (from != null && to != null) {
			querySettings.between("reportDate", from, to);
		} else if (null == from && to != null) {
			before(to);
		} else if (from != null && to == null) {
			after(from);
		}
		return this;
	}

	public OutputValueReporterQuery after(Date date) {
		querySettings.gt("reportDate", date);
		return this;
	}

	public OutputValueReporterQuery before(Date date) {
		querySettings.lt("reportDate", date);
		return this;
	}

}
