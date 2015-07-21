#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import java.util.HashSet;
import java.util.Set;

import ${package}.domain.Bidding;
import ${package}.domain.BiddingStatus;
import ${package}.domain.Person;
import ${package}.query.BiddingQuery;
import ${package}.query.PersonQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 标的详细信息
 * 
 * @author zjzhai
 * 
 */
public class BiddingDetailsAction extends BaseAction {

	private static final long serialVersionUID = -8862195157782128540L;

	private long biddingId = 0l;

	private Bidding bidding;

	/**
	 * 可作为委托人的候选人
	 */
	private Set<Person> principals;

	private BiddingStatus[] biddingStatus = BiddingStatus.values();

	public String execute() {
		bidding = BiddingQuery.createResponsibleOf(getGrantedScope()).id(biddingId).enabled().getSingleResult();
		if (bidding == null) {
			return WORKTABLE;
		}
		principals = new HashSet<Person>(PersonQuery.create().immediateWithSelf(getGrantedScope()).enabled().list());
		return SUCCESS;
	}

	public boolean isUnknow() {
		return bidding.isUnkown();
	}

	public Set<Person> getPrincipals() {
		return principals;
	}

	public Bidding getBidding() {
		return bidding;
	}

	public void setBidding(Bidding bidding) {
		this.bidding = bidding;
	}

	public long getBiddingId() {
		return biddingId;
	}

	public void setBiddingId(long biddingId) {
		this.biddingId = biddingId;
	}

	public BiddingStatus[] getBiddingStatus() {
		return biddingStatus;
	}

}
