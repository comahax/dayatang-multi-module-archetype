#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import org.apache.struts2.convention.annotation.Result;

import ${package}.domain.Bidding;
import ${package}.domain.Person;
import ${package}.query.BiddingQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 设置标的委托人
 * 
 * @author zjzhai
 * 
 */
@Result(name = "success", type = "redirect", location = "bidding-details.action", params = { "biddingId", "%{biddingId}" })
public class BiddingPrincipalSetSubmitAction extends BaseAction {

	private static final long serialVersionUID = -31464098062657443L;

	private boolean result = false;

	private long principalId = 0l;
	private long biddingId = 0l;

	public String execute() throws Exception {
		Bidding bidding = BiddingQuery.getAuthenticateSuccessOf(getGrantedScope(), biddingId);
		if (null == bidding) {
			return WORKTABLE;
		}

		Person principal = Person.get(principalId);
		bidding.setPrincipal(principal);
		projApplication.saveEntity(bidding);
		result = true;
		return SUCCESS;
	}

	public void setPrincipalId(long principalId) {
		this.principalId = principalId;
	}

	public boolean isResult() {
		return result;
	}

	public void setBiddingId(long biddingId) {
		this.biddingId = biddingId;
	}

	public long getBiddingId() {
		return biddingId;
	}

}
