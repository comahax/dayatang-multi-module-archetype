#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金占用
 * User: zjzhai
 * Date: 13-6-20
 * Time: 下午2:47
 */
@Embeddable
public class Capitaltotakeup implements ValueObject {
	private static final long serialVersionUID = 8411917836456390376L;


	/**
     * 成本说明
     */
    @Column(name = "cost_caption")
    private String costCaption;


    /**
     * 时间点
     */
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;


    /**
     * 预计占用资金
     */
    @Column(name = "expected_funds")
    private BigDecimal expectedFunds = BigDecimal.ZERO;


    /**
     * 占用时间（年）
     */
    @Column(name = "year_count")
    private double yearCount = 0;

    /**
     * 利率
     */
    @Column(name = "interest_rate")
    private BigDecimal interestRate = BigDecimal.ZERO;

    /**
     * 占用资金成本
     */
    @Column(name = "cost_funds")
    private BigDecimal costFunds;


    private String remark;


    public Capitaltotakeup() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Capitaltotakeup)) return false;

        Capitaltotakeup that = (Capitaltotakeup) o;

        if (Double.compare(that.yearCount, yearCount) != 0) return false;
        if (costCaption != null ? !costCaption.equals(that.costCaption) : that.costCaption != null) return false;
        if (costFunds != null ? !costFunds.equals(that.costFunds) : that.costFunds != null) return false;
        if (expectedFunds != null ? !expectedFunds.equals(that.expectedFunds) : that.expectedFunds != null) return false;
        if (interestRate != null ? !interestRate.equals(that.interestRate) : that.interestRate != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = costCaption != null ? costCaption.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (expectedFunds != null ? expectedFunds.hashCode() : 0);
        temp = Double.doubleToLongBits(yearCount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (interestRate != null ? interestRate.hashCode() : 0);
        result = 31 * result + (costFunds != null ? costFunds.hashCode() : 0);
        return result;
    }

    public String getCostCaption() {
        return costCaption;
    }

    public void setCostCaption(String costCaption) {
        this.costCaption = costCaption;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getExpectedFunds() {
        return expectedFunds;
    }

    public void setExpectedFunds(BigDecimal expectedFunds) {
        this.expectedFunds = expectedFunds;
    }

    public double getYearCount() {
        return yearCount;
    }

    public void setYearCount(double yearCount) {
        this.yearCount = yearCount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getCostFunds() {
        return costFunds;
    }

    public void setCostFunds(BigDecimal costFunds) {
        this.costFunds = costFunds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
