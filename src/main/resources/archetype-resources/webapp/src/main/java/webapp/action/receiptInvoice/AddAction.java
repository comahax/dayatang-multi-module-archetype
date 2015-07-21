#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.receiptInvoice;

import ${package}.domain.Contract;
import ${package}.domain.ReceiptInvoice;
import ${package}.query.ContractQuery;
import ${package}.webapp.action.BaseAction;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 添加总包发票 User: zjzhai Date: 13-5-3 Time: 下午9:55
 */
public class AddAction extends BaseAction {

	private static final long serialVersionUID = -5879595415032277336L;

	/**
	 * 合同ID
	 */
	private long contractId = 0l;

	/**
	 * 开票日期
	 */
	private Date billingDate;

	/**
	 * 发票编号
	 */

	private String serialNumber;

	/**
	 * 发票金额
	 */
	private BigDecimal amount = BigDecimal.ZERO;
	
	private long id = 0;

	public String execute() throws Exception {

		if (StringUtils.isEmpty(serialNumber) || BigDecimal.ZERO.equals(amount) || contractId <= 0l) {
			return JSON;
		}

		Contract contract = ContractQuery.grantedScopeIn(getGrantedScope()).id(contractId).getSingleResult();

		if (null == contract) {
			return JSON;
		}

		ReceiptInvoice invoice = new ReceiptInvoice(billingDate, serialNumber, amount, contract);
		invoice.log(getCurrentPerson(), new Date());

		try {
			projApplication.saveEntity(invoice);
			id= invoice.getId();
		} catch (org.hibernate.exception.ConstraintViolationException e) {
			errorInfo = "发票编号已存在！";
			return JSON;
		}

		return JSON;
	}
	
	

	public long getId() {
		return id;
	}



	public String getErrorInfo() {
		return errorInfo;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}

}
