#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import com.dayatang.domain.ValueObject;

/**
 * 保证金信息
 * 
 * @author zjzhai
 * 
 */
@Embeddable
public class EarnestMoneyInfo implements ValueObject {
	private static final long serialVersionUID = -7710090516362865920L;

	/**
	 * 保证金金额
	 */
	@Column(name = "amount_of_earnest_money")
	private BigDecimal amountOfEarnestMoney;

	/**
	 * 保证金预估归还日期
	 */
	@Column(name = "estimated_return_time")
	private Date estimatedReturnTime;

	/**
	 * 归还时间
	 */
	@Column(name = "return_time")
	private Date returnTime;

	/**
	 * 备注
	 */
	@Lob
	@Column(name = "earnest_money_remark")
	private String remark;

	public BigDecimal getAmountOfEarnestMoney() {
		return amountOfEarnestMoney;
	}

	public void setAmountOfEarnestMoney(BigDecimal amountOfEarnestMoney) {
		this.amountOfEarnestMoney = amountOfEarnestMoney;
	}

	public Date getEstimatedReturnTime() {
		return estimatedReturnTime;
	}

	public void setEstimatedReturnTime(Date estimatedReturnTime) {
		this.estimatedReturnTime = estimatedReturnTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
