#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.InternalOrganization;
import ${package}.domain.SubcontractInvoice;
import com.dayatang.utils.Assert;

public class SubContractInvoiceQuery extends BaseQuery<SubcontractInvoice> {

	public SubContractInvoiceQuery(QuerySettings<SubcontractInvoice> querySettings) {
		super(querySettings);
	}

	/**
	 * 负责机构在这些机构内的合同
	 */
	public static SubContractInvoiceQuery grantedScopeIn(InternalOrganization scope) {
		Assert.notNull(scope);
		SubContractInvoiceQuery query = new SubContractInvoiceQuery(QuerySettings.create(SubcontractInvoice.class));
		query.querySettings.and(new GeCriterion("contract.grantedScope.leftValue", scope.getLeftValue()), new LeCriterion(
				"contract.grantedScope.rightValue", scope.getRightValue()));
		return query;
	}

	public static SubContractInvoiceQuery create() {
		return new SubContractInvoiceQuery(QuerySettings.create(SubcontractInvoice.class));
	}

}
