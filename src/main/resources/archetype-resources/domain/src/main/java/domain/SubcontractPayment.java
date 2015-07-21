#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.dayatang.domain.QuerySettings;
import ${package}.utils.DocumentTagConstans;

/**
 * 分包付款
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "subcontract_payments")
public class SubcontractPayment extends AbstractCoreEntity {

	private static final long serialVersionUID = 2438924627936467325L;

	/**
	 * 发票
	 */
	@ManyToOne
	@JoinColumn(name = "invoice_id")
	private SubcontractInvoice invoice;

	/**
	 * 付款金额
	 */
	@NotNull
	private BigDecimal amount = BigDecimal.ZERO;

	/**
	 * 支出时间
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "spend_date")
	private Date spendDate;

	/**
	 * 备注
	 */
	@Lob
	@Column(name = "remark")
	private String remark;

	public SubcontractPayment() {

	}

	public SubcontractPayment(SubcontractInvoice invoice, BigDecimal amount, Date spendDate, String remark) {
		super();
		this.invoice = invoice;
		this.amount = amount;
		this.spendDate = spendDate;
		this.remark = remark;
	}

	public static SubcontractPayment findByInvoiceAndId(SubcontractInvoice invoice, long id) {
		return getRepository().getSingleResult(
				QuerySettings.create(SubcontractPayment.class).eq("invoice", invoice).eq("id", id).desc("id"));
	}

	public static List<SubcontractPayment> findBySubcontract(SubContract subContract) {
		return getRepository().find(
				QuerySettings.create(SubcontractPayment.class).eq("invoice.contract", subContract).desc("id"));
	}

	public static List<SubcontractPayment> findByInvoice(SubcontractInvoice invoice2) {
		return getRepository().find(QuerySettings.create(SubcontractPayment.class).eq("invoice", invoice2).desc("id"));
	}

	public void remove() {
		removeDocs();
		super.remove();
	}

	/**
	 * 删除所有项目文档
	 */
	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(DocumentTagConstans.SUBCONTRACT_PAYMENT, getId()))) {
			each.remove();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((invoice == null) ? 0 : invoice.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((spendDate == null) ? 0 : spendDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubcontractPayment other = (SubcontractPayment) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (invoice == null) {
			if (other.invoice != null)
				return false;
		} else if (!invoice.equals(other.invoice))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (spendDate == null) {
			if (other.spendDate != null)
				return false;
		} else if (!spendDate.equals(other.spendDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubcontractPayment [invoice=" + invoice + ", amount=" + amount + ", spendDate=" + spendDate + ", remark="
				+ remark + "]";
	}

	public SubcontractInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(SubcontractInvoice invoice) {
		this.invoice = invoice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getSpendDate() {
		return spendDate;
	}

	public void setSpendDate(Date spendDate) {
		this.spendDate = spendDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
