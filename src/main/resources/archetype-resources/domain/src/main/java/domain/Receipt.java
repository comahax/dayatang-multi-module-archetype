#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import com.dayatang.domain.QuerySettings;
import ${package}.commons.BigDecimalUtils;
import ${package}.utils.DocumentTagConstans;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收款记录
 */
@Entity
@Table(name = "receipts")
public class Receipt extends AbstractCoreEntity {

	private static final long serialVersionUID = -8963824618484138235L;

	/**
	 * 合同
	 */
	@ManyToOne
	@JoinColumn(name = "contract_id")
	private Contract contract;

	/**
	 * 发票
	 */
	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private ReceiptInvoice invoice;

	/**
	 * 收款类型
	 */
	@Column(name = "receipt_type")
	private String receiptType;

	/**
	 * 收款金额
	 */
	private BigDecimal amount;

	/**
	 * 收款时间
	 */
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "received_date")
	private Date receivedDate;

	/**
	 * 备注
	 */
	@Lob
	@Column(name = "receipt_remark")
	private String remark;

	public Receipt() {
	}

	/**
	 * 计算回款率 百分制，如20%
	 * 
	 * @return
	 */
	public static BigDecimal getReceivableRatioOf(SingleContract singleContract) {
		BigDecimal invoiceTotal = ReceiptInvoice.getTotalInvoiceAmountOf(singleContract);
		if (BigDecimal.ZERO.equals(invoiceTotal)) {
			return BigDecimal.ZERO;
		}
		BigDecimal receiptTotal = Receipt.getTotalReceiptAmountOf(singleContract);
		return BigDecimalUtils.percentage(receiptTotal, invoiceTotal);
	}

	/**
	 * 框架合同汇款率
	 * 
	 * @param frameworkContract
	 * @return
	 */
	public static BigDecimal getFrameworkContractReceivableRatioOf(FrameworkContract frameworkContract) {
		BigDecimal invoiceTotal = ReceiptInvoice.getFrameworkContractInvoiceOf(frameworkContract);
		if (BigDecimal.ZERO.equals(invoiceTotal)) {
			return BigDecimal.ZERO;
		}
		BigDecimal receiptTotal = Receipt.getTotalReceiptAmountOf(frameworkContract);
		return BigDecimalUtils.percentage(receiptTotal, invoiceTotal);
	}

	public static BigDecimal getFrameworkContractReceivableRatioOf(BigDecimal receipt, BigDecimal invoice) {
		return BigDecimalUtils.percentage(receipt, invoice);
	}

	/**
	 * 计算项目的回款率
	 * 
	 * @param project
	 * @return
	 */
	public static BigDecimal getReceivableRatioOf(Project project) {
		BigDecimal invoiceTotal = ReceiptInvoice.getTotalInvoiceAmountOf(project);
		if (BigDecimal.ZERO.equals(invoiceTotal)) {
			return BigDecimal.ZERO;
		}
		BigDecimal receiptTotal = Receipt.getTotalReceiptAmountOf(project);
		return BigDecimalUtils.percentage(receiptTotal, invoiceTotal);
	}

	public static List<Receipt> findByContract(Contract contract) {
		return getRepository().find(QuerySettings.create(Receipt.class).eq("contract", contract));
	}

	public static List<Receipt> findByReceiptInvoice(ReceiptInvoice invoice) {
		return getRepository().find(QuerySettings.create(Receipt.class).eq("invoice", invoice));
	}

	/**
	 * 单项合同收款总额
	 * 
	 * @param singleContract
	 * @return
	 */
	public static BigDecimal getTotalReceiptAmountOf(Contract contract) {
		String sql = "SELECT SUM(o.amount) FROM Receipt o WHERE o.contract = :contract";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract", contract);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	public static BigDecimal getTotalReceiptAmountOf(FrameworkContract contract) {
		BigDecimal total = BigDecimal.ZERO;

		List<SingleContract> singleContracts = SingleContract.findByFrameworkContract(contract);

		List<Contract> contracts = new ArrayList<Contract>();
		contracts.add(contract);
		if (null != singleContracts) {
			contracts.addAll(singleContracts);
		}
		for (Contract each : contracts) {
			total = total.add(getTotalReceiptAmountOf(each));
		}

		return total;
	}

	/**
	 * 某张已开发票对应的收款总额
	 * 
	 * @param invoice
	 * @return
	 */
	public static BigDecimal getTotalReceiptAmountOf(Invoice invoice) {
		String sql = "SELECT SUM(o.amount) FROM Receipt o WHERE o.invoice = :invoice";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invoice", invoice);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 项目的收款总额
	 * 
	 * @param project
	 * @return
	 */
	public static BigDecimal getTotalReceiptAmountOf(Project project) {
		String sql = "SELECT SUM(o.amount) FROM Receipt o WHERE o.contract.project = :project";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project", project);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}


	/**
	 * 某机构的收款总额
	 * 
	 * @param organization
	 * @return
	 */
	public static BigDecimal getTotalReceiptAmountOf(InternalOrganization organization) {
		String sql = "SELECT SUM(o.amount) FROM Receipt o WHERE o.contract.project.responsibleDivision = :org";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("org", organization);
		BigDecimal result = getRepository().getSingleResult(sql, params, BigDecimal.class);
		return result == null ? BigDecimal.ZERO : result;
	}

	/**
	 * 收款率低于100的
	 * 
	 * @return
	 */
	public static boolean isLackOfReceipt(SingleContract contract) {
		return BigDecimalUtils.aGtb(new BigDecimal(100), getReceivableRatioOf(contract));
	}

	@Override
	public void remove() {
		removeDocs();
		super.remove();
	}

	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(DocumentTagConstans.RECEIPT, getId()))) {
			each.remove();
		}
	}

	public static Receipt get(long receiptId) {
		return Receipt.get(Receipt.class, receiptId);
	}

	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Receipt))
			return false;
		Receipt that = (Receipt) other;
		return new EqualsBuilder().append(this.getContract(), that.getContract()).append(this.getInvoice(), that.getInvoice()).append(receiptType, that.receiptType).append(receivedDate, getReceivedDate()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(invoice).append(amount).append(receiptType).toHashCode();
	}

	public String toString() {
		return new StringBuilder().append(this.getContract()).append("  ").append(invoice).append("  ").append(amount).append("  ").append("  ").append(receiptType).toString();
	}

	public String getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public ReceiptInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(ReceiptInvoice invoice) {
		this.invoice = invoice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

}
