#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.Bidding;
import ${package}.domain.BiddingStatus;
import ${package}.query.BiddingQuery;
import ${package}.webapp.action.BaseAction;

@Result(name = "success", type = "redirect", location = "bidding-details.action", params = { "biddingId", "%{biddingId}" })
public class BiddingResultSetSubmitAction extends BaseAction {

	private static final long serialVersionUID = -4731607868749334938L;

	private long biddingId = 0l;
	private BiddingStatus biddingStatus = BiddingStatus.BIDDING_FAIL;

	public String execute() throws Exception {
		Bidding bidding = BiddingQuery.getAuthenticateSuccessOf(getGrantedScope(), biddingId);
		if (null == bidding) {
			return WORKTABLE;
		}
		bidding.setBiddingStatus(biddingStatus);
		projApplication.saveEntity(bidding);
		return SUCCESS;
	}

	public long getBiddingId() {
		return biddingId;
	}

	public void setBiddingId(long biddingId) {
		this.biddingId = biddingId;
	}

	public BiddingStatus getBiddingStatus() {
		return biddingStatus;
	}

	public void setBiddingStatus(BiddingStatus biddingStatus) {
		this.biddingStatus = biddingStatus;
	}

}
