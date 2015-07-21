#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import java.util.Date;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.Bidding;
import ${package}.query.BiddingQuery;
import ${package}.webapp.action.BaseAction;

@Result(name = "success", type = "redirect", location = "bidding-details.action", params = { "biddingId", "%{biddingId}" })
public class BiddingTenderDateSetSubmitAction extends BaseAction {

	private static final long serialVersionUID = -31464098062657443L;

	private boolean result = false;

	private Date tenderDate;
	private long biddingId = 0l;

	public String execute() throws Exception {
		Bidding bidding = BiddingQuery.getAuthenticateSuccessOf(getGrantedScope(), biddingId);
		if (null == bidding) {
			return WORKTABLE;
		}
		bidding.setTenderDate(tenderDate);
		projApplication.saveEntity(bidding);
		result = true;
		return SUCCESS;
	}

	public long getBiddingId() {
		return biddingId;
	}

	public void setTenderDate(Date tenderDate) {
		this.tenderDate = tenderDate;
	}

	public boolean isResult() {
		return result;
	}

	public void setBiddingId(long biddingId) {
		this.biddingId = biddingId;
	}

}
