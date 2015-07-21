#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dayatang.domain.QueryCriterion;
import com.dayatang.domain.QuerySettings;
import com.dayatang.domain.internal.EqCriterion;
import com.dayatang.domain.internal.GeCriterion;
import com.dayatang.domain.internal.LeCriterion;
import ${package}.domain.Contract;
import ${package}.domain.FrameworkContract;
import ${package}.domain.InternalOrganization;
import ${package}.domain.ReceiptInvoice;
import ${package}.domain.SingleContract;
import com.dayatang.utils.Assert;
import com.dayatang.utils.DateUtils;

public class ReceiptInvoiceQuery extends BaseQuery<ReceiptInvoice> {

	private ReceiptInvoiceQuery() {
		super(QuerySettings.create(ReceiptInvoice.class));
	}

	/**
	 * 负责机构在这些机构内的合同
	 */
	public static ReceiptInvoiceQuery grantedScopeIn(InternalOrganization scope) {
		Assert.notNull(scope);
		ReceiptInvoiceQuery query = new ReceiptInvoiceQuery();
		query.querySettings.and(new GeCriterion("contract.grantedScope.leftValue", scope.getLeftValue()), new LeCriterion(
				"contract.grantedScope.rightValue", scope.getRightValue()));
		return query;
	}

	public ReceiptInvoiceQuery between(Date from, Date to) {
		from = from == null ? DateUtils.MIN_DATE : from;
		to = to == null ? DateUtils.MAX_DATE : to;
		querySettings.between("billingDate", from, to);
		return this;
	}

	public ReceiptInvoiceQuery ascBillingDate() {
		querySettings.asc("billingDate");
		return this;
	}

	public ReceiptInvoiceQuery descBillingDate() {
		querySettings.desc("billingDate");
		return this;
	}

	public ReceiptInvoiceQuery id(long id) {
		querySettings.eq("id", id);
		return this;
	}

	public ReceiptInvoiceQuery contract(Contract contract) {
		querySettings.eq("contract", contract);
		return this;
	}

	/**
	 * 框架合同下的所有的及框架合同下单项合同的收款发票
	 * 
	 * @param frameworkContract
	 * @return
	 */
	public ReceiptInvoiceQuery frameworkContract(FrameworkContract frameworkContract) {

		List<Contract> contracts = new ArrayList<Contract>();
		List<SingleContract> singleContracts = SingleContractQuery.create().frameworkContract(frameworkContract).list();
		if (singleContracts != null) {
			contracts.addAll(singleContracts);
		}

		contracts.add(frameworkContract);

		if (contracts.size() < 2) {
			querySettings.eq("contract", frameworkContract);

		} else {
			List<QueryCriterion> criterions = new ArrayList<QueryCriterion>();

			for (Contract contract : contracts) {
				criterions.add(new EqCriterion("contract", contract));
			}
			querySettings.or(criterions.toArray(new EqCriterion[criterions.size()]));

		}

		return this;

	}

	public ReceiptInvoiceQuery serialNumberContains(String serialNumber) {
		querySettings.containsText("serialNumber", serialNumber);
		return this;
	}

	public ReceiptInvoiceQuery singleContract(SingleContract contract) {
		querySettings.eq("contract", contract);
		return this;
	}
}
