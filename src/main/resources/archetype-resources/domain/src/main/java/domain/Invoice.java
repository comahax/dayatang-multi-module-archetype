#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.domain;

import static ${package}.utils.DocumentTagConstans.*;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * 发票基本信息
 * 
 * @author zjzhai
 * 
 */
@Entity
@Table(name = "invoices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category", discriminatorType = DiscriminatorType.STRING)
public abstract class Invoice extends AbstractCoreEntity {

	private static final long serialVersionUID = -1109558017078650218L;

	/**
	 * 开票日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "billing_date")
	private Date billingDate;

	/**
	 * 发票编号
	 */

	@Column(name = "serial_number", nullable = false, unique = true)
	private String serialNumber;

	/**
	 * 发票金额
	 */
	@Column(name = "invoice_amount")
	@NotNull
	private BigDecimal amount = BigDecimal.ZERO;

	public Invoice() {

	}
	
	public void remove(){
		removeDocs();
		super.remove();
	}
	
	private void removeDocs() {
		for (Document each : Document.findByOneTag(new DocumentTag(INVOICE, getId()))) {
			each.remove();
		}
	}

	public static Invoice get(long invoiceId) {
		return Invoice.get(Invoice.class, invoiceId);
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
