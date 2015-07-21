#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.bidding;

import java.util.List;

import ${package}.domain.Bidding;
import ${package}.pager.PageList;
import ${package}.query.BaseQuery;
import ${package}.query.BiddingQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 
 * @author zjzhai
 * 
 */
public class BiddingListAction extends BaseAction {

	private static final long serialVersionUID = -5628583249318536378L;

	private String earnestMoneyReturned = null;

	private String win = null;

	private List<Bidding> biddings;

	public String execute() {
		PageList<Bidding> pageList = createPageList(initQuery());
		if (pageList != null) {
			biddings = pageList.getData();
			//page = pageList.getPage();
		}
		return SUCCESS;
	}

	/**
	 * 根据过滤查询
	 * 
	 * @return
	 */
	private BaseQuery<Bidding> initQuery() {
		return BiddingQuery.createResponsibleOf(getGrantedScope()).winBiddingIf(strToBoolean(win))
				.earnestMoneyReturnedIf(strToBoolean(earnestMoneyReturned)).enabled();
	}

	public List<Bidding> getBiddings() {
		return biddings;
	}

	public String getWin() {
		return win;
	}

	public void setWin(String win) {
		this.win = win;
	}

	public String getEarnestMoneyReturned() {
		return earnestMoneyReturned;
	}

	public void setEarnestMoneyReturned(String earnestMoneyReturned) {
		this.earnestMoneyReturned = earnestMoneyReturned;
	}

}
