#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dayatang.domain.AbstractEntity;
import com.dayatang.domain.QuerySettings;

/**
 * 日志
 * 
 * @author mluo
 * 
 */
@Entity
@Table(name = "log_records")
@Deprecated
public class LogRecord extends AbstractEntity {

	private static final long serialVersionUID = -1043805711892665046L;

	private String username;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "happen_date")
	private Date happenDate;

	/**
	 * 进行的操作
	 */
	private String operation;

	/**
	 * 操作的IP
	 */
	private String ip;

	public LogRecord(String username, String operation, String IP) {
		this();
		this.username = username;
		this.ip = IP;
		this.operation = operation;
		this.happenDate = new Date();
	}

	/**
	 * 倒序ID查所有的日志
	 * 
	 * @return
	 */
	public static List<LogRecord> allLogRecordsDescId() {
		return getRepository().find(QuerySettings.create(LogRecord.class).desc("id"));
	}

	private LogRecord() {
	}

	public String getIp() {
		return ip;
	}

	public String getUsername() {
		return username;
	}

	public Date getHappenDate() {
		return happenDate;
	}

	public String getOperation() {
		return operation;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LogRecord)) {
			return false;
		}
		LogRecord castOther = (LogRecord) other;
		return new EqualsBuilder().append(getUsername(), castOther.getUsername())
				.append(getHappenDate(), castOther.getHappenDate()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getUsername()).append(getHappenDate()).toHashCode();
	}

	public String toString() {
		return getUsername() + ":" + getOperation();
	}

}
