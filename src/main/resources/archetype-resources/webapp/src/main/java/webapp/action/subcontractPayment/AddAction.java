#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractPayment;

import java.math.BigDecimal;
import java.util.Date;

import ${package}.commons.BigDecimalUtils;
import ${package}.domain.SubcontractInvoice;
import ${package}.domain.SubcontractPayment;
import ${package}.query.SubContractInvoiceQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 添加付款
 * 
 * @author zjzhai
 * 
 */
public class AddAction extends BaseAction {

	private static final long serialVersionUID = 1161394580043037755L;

	/**
	 * 发票
	 */
	private long invoiceId = 0l;

	/**
	 * 付款金额
	 */
	private BigDecimal amount = BigDecimal.ZERO;

	/**
	 * 支出时间
	 */
	private Date spendDate;

	/**
	 * 备注
	 */
	private String remark;

	public String execute() {
		if (invoiceId <= 0l || null == amount || BigDecimalUtils.leZero(amount)) {
			errorInfo = "传入参数不正确";
			return JSON;
		}

		SubcontractInvoice invoice = SubContractInvoiceQuery.grantedScopeIn(getGrantedScope()).id(invoiceId).getSingleResult();

		if (null == invoice) {
			errorInfo = "该发票不存在";
			return JSON;
		}

		SubcontractPayment payment = new SubcontractPayment(invoice, amount, spendDate, remark);

		projApplication.saveEntity(payment);
		
		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setSpendDate(Date spendDate) {
		this.spendDate = spendDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
