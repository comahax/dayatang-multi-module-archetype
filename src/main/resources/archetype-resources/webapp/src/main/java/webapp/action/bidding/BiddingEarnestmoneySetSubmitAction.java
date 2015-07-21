#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.Bidding;
import ${package}.domain.EarnestMoneyInfo;
import ${package}.query.BiddingQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 设置保证金
 * 
 * @author zjzhai
 * 
 */
@Result(name = "success", type = "redirect", location = "bidding-details.action", params = { "biddingId", "%{biddingId}" })
public class BiddingEarnestmoneySetSubmitAction extends BaseAction {

	private static final long serialVersionUID = -31464098062657443L;

	private Date returnTime;

	private Date estimatedReturnTime;

	private BigDecimal amountOfEarnestMoney;

	private String remark;

	private boolean result = false;

	private long biddingId = 0l;

	public String execute() throws Exception {
		Bidding bidding = BiddingQuery.getAuthenticateSuccessOf(getGrantedScope(), biddingId);
		if (null == bidding) {
			return WORKTABLE;
		}
		EarnestMoneyInfo earnestMoneyInfo = bidding.getEarnestMoneyInfo();
		if (earnestMoneyInfo == null) {
			earnestMoneyInfo = new EarnestMoneyInfo();
		}

		if (returnTime != null) {
			earnestMoneyInfo.setReturnTime(returnTime);
		}
		if (remark != null) {
			earnestMoneyInfo.setRemark(remark);
		}
		if (estimatedReturnTime != null) {
			earnestMoneyInfo.setEstimatedReturnTime(estimatedReturnTime);
		}

		if (amountOfEarnestMoney != null) {
			earnestMoneyInfo.setAmountOfEarnestMoney(amountOfEarnestMoney);
		}
		bidding.setEarnestMoneyInfo(earnestMoneyInfo);
		projApplication.saveEntity(bidding);
		result = true;
		return SUCCESS;
	}

	public long getBiddingId() {
		return biddingId;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public void setEstimatedReturnTime(Date estimatedReturnTime) {
		this.estimatedReturnTime = estimatedReturnTime;
	}

	public void setAmountOfEarnestMoney(BigDecimal amountOfEarnestMoney) {
		this.amountOfEarnestMoney = amountOfEarnestMoney;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isResult() {
		return result;
	}

	public void setBiddingId(long biddingId) {
		this.biddingId = biddingId;
	}

}
