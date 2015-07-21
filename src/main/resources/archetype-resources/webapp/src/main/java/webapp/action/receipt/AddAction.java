#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receipt;

import ${package}.domain.Receipt;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.ReceiptInvoiceQuery;
import ${package}.webapp.action.BaseAction;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 添加收款 User: zjzhai Date: 13-5-5 Time: 上午12:28
 */
public class AddAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4212275382827294170L;

	/**
	 * 发票
	 */
	private long invoiceId = 0l;

	/**
	 * 收款类型
	 */
	private String receiptType;

	/**
	 * 收款金额
	 */
	private BigDecimal amount;

	/**
	 * 收款时间
	 */
	private Date receivedDate;

	/**
	 * 备注
	 */
	private String remark;

	@Override
	public String execute() throws Exception {

		if (invoiceId <= 0) {
			errorInfo = "找不到该发票";
			return JSON;
		}

		ReceiptInvoice invoice = ReceiptInvoiceQuery.grantedScopeIn(getGrantedScope()).id(invoiceId).getSingleResult();

		if (null == invoice) {
			errorInfo = "找不到该发票";
			return JSON;
		}

		Receipt receipt = new Receipt();
		receipt.setInvoice(invoice);
		receipt.setAmount(amount);
		receipt.setContract(invoice.getContract());
		receipt.setReceiptType(receiptType);
		receipt.setReceivedDate(receivedDate);
		receipt.setRemark(remark);
		receipt.log(getCurrentPerson(), new Date());
		projApplication.saveEntity(receipt);

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
