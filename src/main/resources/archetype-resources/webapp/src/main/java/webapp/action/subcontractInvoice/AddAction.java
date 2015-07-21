#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.action.subcontractInvoice;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import ${package}.commons.BigDecimalUtils;
import ${package}.domain.SubContract;
import ${package}.domain.SubcontractInvoice;
import ${package}.query.SubContractQuery;
import ${package}.webapp.action.BaseAction;

/**
 * 添加分包发票
 * 
 * @author zjzhai
 * 
 */
public class AddAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Long subContractId = 0l;

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


    /**
     * 用于返回给前端页面
     */
    private long id = 0l;

	public String execute() {
		if (StringUtils.isEmpty(serialNumber) || null == amount || BigDecimalUtils.leZero(amount) || subContractId <= 0l) {
			errorInfo = "传入参数不合法!";
			return JSON;
		}

		SubContract contract = SubContractQuery.grantedScopeIn(getGrantedScope()).id(subContractId).getSingleResult();

		if (null == contract) {
			errorInfo = "该分包合同不存在";
			return JSON;
		}

		SubcontractInvoice invoice = new SubcontractInvoice();
		invoice.setAmount(amount);
		invoice.setBillingDate(billingDate);
		invoice.setContract(contract);
		invoice.setSerialNumber(serialNumber);
		invoice.setContract(contract);

		try {
			projApplication.saveEntity(invoice);
            id = invoice.getId();
		} catch (Exception e) {
			errorInfo = "添加失败。可能是发票编号有重复。";
			return JSON;
		}

		return JSON;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setSubContractId(Long subContractId) {
		this.subContractId = subContractId;
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


    public long getId() {
        return id;
    }
}
