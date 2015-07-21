#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;


import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.BiddingRequest;
import ${package}.domain.InternalOrganization;
import com.dayatang.utils.Assert;

/**
 * 投标请求查询
 * 
 * @author Administrator
 * 
 */
public class BiddingRequestQuery extends BaseQuery<BiddingRequest> {

	private BiddingRequestQuery() {
		super(QuerySettings.create(BiddingRequest.class));
	}

	/**
	 * 投标请求发生在一些机构内的
	 * 
	 * @param internals
	 * @return
	 */
	public static BiddingRequestQuery createAndRequestIn(InternalOrganization scope) {
		Assert.notNull(scope);
		BiddingRequestQuery query = new BiddingRequestQuery();
		query.querySettings.and(new GeCriterion("releasePerson.organization.leftValue", scope.getLeftValue()), new LeCriterion(
				"releasePerson.organization.rightValue", scope.getRightValue()));
		return query;
	}

	/**
	 * 按照发起投标请求时间升序排列
	 * 
	 * @return
	 */
	public BiddingRequestQuery ascReleaseDate() {
		querySettings.asc("releaseDate");
		return this;
	}

	/**
	 * 按照项目金额降序排列
	 * 
	 * @return
	 */
	public BiddingRequestQuery descProjectAmount() {
		querySettings.desc("projectAmount");
		return this;
	}

}
