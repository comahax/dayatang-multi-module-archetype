#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;
import ${package}.commons.BigDecimalUtils;
import ${package}.pager.PageList;
import com.dayatang.utils.DateUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.*;

import static ${package}.utils.DocumentTagConstans.RECEIPT_INVOICE;

/**
 * 收款发票
 * 
 * @author zjzhai
 */
@Entity
@DiscriminatorValue("RECEIPT")
public class ReceiptInvoice extends Invoice {

	private static final long serialVersionUID = 7620961971360727198L;

	@ManyToOne
	@JoinColumn(name = "contract_id")
	private Contract contract;

	@SuppressWarnings("unused")
	private ReceiptInvoice() {
	}

	public ReceiptInvoice(Date billingDate, String serialNumber, BigDecimal amount, Contract contract) {
		setBillingDate(billingDate);
		setSerialNumber(serialNumber);
		setAmount(amount);
		this.contract = contract;
	}

	/**
	 * 已开收款发票总额
	 * 
	 * @return
	 */
	public static BigDecimal getTotalInvoiceAmountOf(Contract contract) {
		String sql = "SELECT SUM(o.amount) FROM ReceiptInvoice o WHERE o.contract = :contract";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract", contract);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 已开收款发票总额
	 * 
	 * @return
	 */
	public static BigDecimal getTotalInvoiceAmountOf(SingleContract contract) {
		String sql = "SELECT SUM(o.amount) FROM ReceiptInvoice o WHERE o.contract = :contract";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract", contract);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 获取框架合同开票总金额
	 * 
	 * @param frameworkContract
	 * @return
	 */
	public static BigDecimal getFrameworkContractInvoiceOf(FrameworkContract frameworkContract) {
		BigDecimal total = BigDecimal.ZERO;
		List<SingleContract> singleContracts = SingleContract.findByFrameworkContract(frameworkContract);
		for (SingleContract singleContract : singleContracts) {
			total = total.add(getTotalInvoiceAmountOf(singleContract));
		}
		total = total.add(getTotalInvoiceAmountOf(frameworkContract));
		return total;
	}

	/**
	 * 一个项目的所开票金额
	 * 
	 * @param project
	 * @return
	 */
	public static BigDecimal getTotalInvoiceAmountOf(Project project) {
		String sql = "SELECT SUM(o.amount) FROM ReceiptInvoice o WHERE o.contract.project = :project";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 一个机构的所开票金额总额
	 * 
	 * @param org
	 * @return
	 */
	public static BigDecimal getTotalInvoiceAmountOf(InternalOrganization org) {
		String sql = "SELECT SUM(o.amount) FROM ReceiptInvoice o WHERE o.contract.project.responsibleDivision = :org";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("org", org);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	// TODO 应该使用jpql

	/**
	 * 得到某项目下所有未收完款的发票
	 * 
	 * @return
	 */
	public static List<ReceiptInvoice> findAllNotbackReceiptInvoiceOf(SingleContract singleContract) {
		List<ReceiptInvoice> invoices = findBy(singleContract);
		if (null == invoices || invoices.isEmpty()) {
			return new ArrayList<ReceiptInvoice>();
		}

		List<ReceiptInvoice> results = new ArrayList<ReceiptInvoice>();
		for (ReceiptInvoice each : invoices) {
			BigDecimal receiptAmount = Receipt.getTotalReceiptAmountOf(each);
			if (receiptAmount == null || BigDecimalUtils.eqZero(receiptAmount)
					|| BigDecimalUtils.aGtb(each.getAmount(), receiptAmount)) {
				results.add(each);
			}
		}
		return results;
	}

	public static PageList<ReceiptInvoice> findBy(Date from, Date to, Boolean completed, InternalOrganization scope, int page,
			int pageSize) {

		StringBuilder sb = new StringBuilder("SELECT r FROM ReceiptInvoice r ");
		sb.append(" WHERE ");
		sb.append(" r.billingDate > :from AND r.billingDate < :to ");
		sb.append(" AND ");
		sb.append(" r.contract.grantedScope.leftValue >= :leftValue AND r.contract.grantedScope.rightValue <= :rightValue  ");
		if (completed != null) {
			sb.append(" AND (( ");
			sb.append(" r.amount " + (completed ? " <= " : " > ")
					+ " (SELECT SUM(o.amount) FROM Receipt o GROUP BY o.invoice.id HAVING o.invoice.id = r.id)) ");
			if (!completed) {
				sb.append(" OR ")
						.append("( NOT EXISTS (SELECT o.id FROM Receipt o WHERE o.invoice.id = r.id AND o.invoice.billingDate > :from AND o.invoice.billingDate < :to))");
			}
			sb.append(" )) ");
		}
		sb.append(" ORDER BY r.billingDate ASC ");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("from", from == null ? DateUtils.MIN_DATE : from);
		params.put("to", to == null ? DateUtils.MAX_DATE : to);
		params.put("leftValue", scope.getLeftValue());
		params.put("rightValue", scope.getRightValue());

		page = page <= 0 ? 0 : page - 1;
		return PageList.create(getRepository().find(sb.toString(), params, ReceiptInvoice.class), page, pageSize);
	}

	/**
	 * 是否收款完成
	 * 
	 * @return
	 */
	public Boolean isReceiptCompleted() {
		String sql = "SELECT SUM(o.amount) FROM Receipt o WHERE o.invoice.id = :invoiceId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invoiceId", getId());
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		if (null == result) {
			return false;
		}
		//发票金额大于实际收款金额
		if (BigDecimalUtils.aGtb(getAmount(), result)) {
			return false;
		}
		return true;
	}

	public Boolean isNotReceiptCompleted() {
		return !isReceiptCompleted();
	}

	public static List<ReceiptInvoice> findBy(Contract contract) {
		return getRepository().find(QuerySettings.create(ReceiptInvoice.class).eq("contract", contract).desc("billingDate"));
	}

	public static ReceiptInvoice get(long invoiceId) {
		return ReceiptInvoice.get(ReceiptInvoice.class, invoiceId);
	}

	public void remove() {
		removeReceipts();
		removeDocs();
		super.remove();
	}

	private void removeReceipts() {
		for (Receipt each : Receipt.findByReceiptInvoice(this)) {
			each.remove();
		}
	}

	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(RECEIPT_INVOICE, getId()))) {
			each.remove();
		}
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Invoice)) {
			return false;
		}
		Invoice that = (Invoice) other;
		return new EqualsBuilder().append(getSerialNumber(), that.getSerialNumber()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getSerialNumber()).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append(getSerialNumber()).append(getAmount()).toString();
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

}
