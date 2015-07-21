#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dayatang.domain.ValueObject;

@Embeddable
public class TimeRange implements ValueObject {
	private static final long serialVersionUID = -2761803874074416964L;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	/*
	 * 报名结束日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;
	
	TimeRange() {
	}

	public TimeRange(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		if (startDate != null && endDate == null) {
			return "< " + startDate.toString();
		}

		if (startDate.after(endDate)) {
			return startDate.toString() + " < [applyDate] < " + endDate.toString();
		}

		return super.toString();
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
