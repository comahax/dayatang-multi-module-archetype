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
public class BiddingPrequalificationDateSetSubmitAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private boolean result = false;

	private Date prequalificationDate;
	private long biddingId = 0l;

	public String execute() throws Exception {
		Bidding bidding = BiddingQuery.getAuthenticateSuccessOf(getGrantedScope(), biddingId);
		if (null == bidding) {
			return WORKTABLE;
		}
		bidding.setPrequalificationDate(prequalificationDate);
		projApplication.saveEntity(bidding);
		result = true;
		return SUCCESS;
	}

	public long getBiddingId() {
		return biddingId;
	}

	public void setPrequalificationDate(Date prequalificationDate) {
		this.prequalificationDate = prequalificationDate;
	}

	public boolean isResult() {
		return result;
	}

	public void setBiddingId(long biddingId) {
		this.biddingId = biddingId;
	}


	

}
