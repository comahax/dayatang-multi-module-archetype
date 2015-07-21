#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import java.math.BigDecimal;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.Bidding;
import ${package}.query.BiddingQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 设置标的项目金额
 * 
 * @author zjzhai
 * 
 */
@Result(name = "success", type = "redirect", location = "bidding-details.action", params = { "biddingId", "%{biddingId}" })
public class BiddingProjectAmountSetSubmitAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private long biddingId = 0l;

	private BigDecimal projectAmount = BigDecimal.ZERO;

	public String execute() throws Exception {
		Bidding bidding = BiddingQuery.getAuthenticateSuccessOf(getGrantedScope(), biddingId);
		if (null == bidding) {
			return WORKTABLE;
		}
		bidding.setProjectAmount(projectAmount);
		projApplication.saveEntity(bidding);
		return SUCCESS;
	}

	public void setBiddingId(long biddingId) {
		this.biddingId = biddingId;
	}

	public void setProjectAmount(BigDecimal projectAmount) {
		this.projectAmount = projectAmount;
	}

	public long getBiddingId() {
		return biddingId;
	}

}
