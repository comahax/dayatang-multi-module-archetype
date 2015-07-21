#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.Bidding;
import ${package}.domain.BiddingStatus;
import ${package}.domain.InternalOrganization;

public class BiddingQuery extends BaseQuery<Bidding> {

	private BiddingQuery() {
		super(QuerySettings.create(Bidding.class));
	}

	public static BiddingQuery createResponsibleOf(InternalOrganization scope) {
		BiddingQuery query = new BiddingQuery();
		query.querySettings.and(new GeCriterion("responsibleDivision.leftValue", scope.getLeftValue()), new LeCriterion(
				"responsibleDivision.rightValue", scope.getRightValue()));
		return query;
	}

	/**
	 * 用户访问范围下的某ID的标
	 * 
	 * @param scope
	 * @param id
	 * @return
	 */
	public static Bidding getAuthenticateSuccessOf(InternalOrganization scope, long id) {
		return BiddingQuery.createResponsibleOf(scope).id(id).enabled().getSingleResult();
	}

	/**
	 * 中标与否
	 * 
	 * @param winBidding
	 * @return
	 */
	public BiddingQuery winBiddingIf(Boolean winBidding) {
		if (null == winBidding) {
			return this;
		}
		return winBidding ? win() : notWin();
	}

	public BiddingQuery win() {
		querySettings.eq("biddingStatus", BiddingStatus.BIDDING_WIN);
		return this;
	}

	public BiddingQuery notWin() {
		querySettings.eq("biddingStatus", BiddingStatus.BIDDING_FAIL);
		return this;
	}

	/**
	 * 未知中标结果
	 * 
	 * @return
	 */
	public BiddingQuery notKnowStatusOfBidding() {
		querySettings.eq("biddingStatus", BiddingStatus.BIDDING_UNKNOW);
		return this;
	}

	/**
	 * 保证金未归还
	 * 
	 * @param returned
	 * @return
	 */
	public BiddingQuery earnestMoneyReturnedIf(Boolean returned) {
		if (null == returned) {
			return this;
		}
		return returned ? earnestMoneyReturned() : earnestMoneyUnreturned();
	}

	public BiddingQuery earnestMoneyUnreturned() {
		querySettings.notNull("earnestMoneyInfo.amountOfEarnestMoney").isNull("earnestMoneyInfo.returnTime");
		return this;
	}

	public BiddingQuery earnestMoneyReturned() {
		querySettings.notNull("earnestMoneyInfo.amountOfEarnestMoney").notNull("earnestMoneyInfo.returnTime");
		return this;
	}

	/**
	 * 按照项目金额降序排列
	 * 
	 * @return
	 */
	public BiddingQuery descProjectAmount() {
		querySettings.desc("projectAmount");
		return this;
	}

	public BiddingQuery id(long biddingId) {
		querySettings.eq("id", biddingId);
		return this;
	}

}
