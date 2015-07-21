#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;
import ${package}.query.OutputValueReporterQuery;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 产值汇报
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "outputvalue_reporter")
public class OutputValueReporter extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "outputvalue_id")
	private OutputValue outputValue;

	@OneToOne
	@JoinColumn(name = "reporter_id")
	private Person reporter;

	@Temporal(TemporalType.DATE)
	private Date reportDate;

	public OutputValueReporter() {
		super();
	}

	public OutputValueReporter(Person reporter, Date reportDate, OutputValue outputValue) {
		super();
		this.outputValue = outputValue;
		this.reporter = reporter;
		this.reportDate = reportDate;
	}

	public static List<OutputValueReporter> findBy(Person person) {
		return new OutputValueReporterQuery().reporter(person).list();
	}



	/**
	 * 处理重复报产值的情况
	 */
	public void save() {
		if (isRepeatReport(getOutputValue())) {
			OutputValueReporter old = findBy(getOutputValue());
			this.outputValue = old.getOutputValue();
		}
		super.save();
	}

	public static boolean isRepeatReport(OutputValue outputValue) {
		return findBy(outputValue) != null;
	}

	public static OutputValueReporter findBy(OutputValue outputValue) {
		QuerySettings<OutputValueReporter> settings = QuerySettings.create(OutputValueReporter.class).eq("outputValue",
				outputValue);
		return getRepository().getSingleResult(settings);
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof OutputValueReporter))
			return false;
		OutputValueReporter that = (OutputValueReporter) other;
		return new EqualsBuilder().append(getOutputValue(), that.getOutputValue()).append(getReporter(), that.getReporter())
				.append(getReportDate(), that.getReportDate()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getOutputValue()).append(getReporter()).append(getReportDate()).toHashCode();
	}

	public String toString() {
		return new StringBuilder().append(getOutputValue()).append(",").append(getReporter()).append(",")
				.append(getReportDate()).toString();
	}

	public OutputValue getOutputValue() {
		return outputValue;
	}

	public void setOutputValue(OutputValue outputValue) {
		this.outputValue = outputValue;
	}

	public Person getReporter() {
		return reporter;
	}

	public void setReporter(Person reporter) {
		this.reporter = reporter;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

}
