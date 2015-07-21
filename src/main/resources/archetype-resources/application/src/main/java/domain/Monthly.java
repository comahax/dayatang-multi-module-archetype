#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.dayatang.domain.ValueObject;

/**
 * 月度
 *
 * @author zjzhai
 */
@Embeddable
public class Monthly implements ValueObject, Comparable<Monthly> {

    private static final long serialVersionUID = 9000916793083181651L;

    @Column(name = "montly_year")
    private int year;

    @Column(name = "montly_month")
    private int month;

    public Monthly() {
    }

    /**
     * 1 Base
     *
     * @param year
     * @param month
     */
    public Monthly(int year, int month) {
        this.year = year;
        this.month = month;
    }

    /**
     * 1 Base
     *
     * @param date
     */
    public Monthly(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
    }

    public String toString() {
        return year + "-" + month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int compareTo(Monthly other) {
        int result = this.year - other.year;
        if (result != 0) {
            return result;
        }
        return this.month - other.month;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + month;
        result = prime * result + year;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Monthly)) {
            return false;
        }
        Monthly other = (Monthly) obj;
        return new EqualsBuilder().append(this.year, other.year).append(this.month, other.month).isEquals();
    }


}
