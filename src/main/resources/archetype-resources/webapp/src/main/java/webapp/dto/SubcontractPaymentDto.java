#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.webapp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import ${package}.domain.SubcontractPayment;

public class SubcontractPaymentDto {

	private Long id;
	/**
	 * 发票
	 */
	private SubContractInvoiceDto invoice;

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

	@SuppressWarnings("unused")
	private SubcontractPaymentDto() {

	}

	public SubcontractPaymentDto(SubcontractPayment subcontractPayment) {
		id = subcontractPayment.getId();
		invoice = new SubContractInvoiceDto(subcontractPayment.getInvoice());
		amount = subcontractPayment.getAmount();
		spendDate = subcontractPayment.getSpendDate();
		remark = subcontractPayment.getRemark();
	}

	public static List<SubcontractPaymentDto> createBy(Collection<SubcontractPayment> subcontractPayments) {
		if (null == subcontractPayments || subcontractPayments.isEmpty()) {
			return new ArrayList<SubcontractPaymentDto>();
		}

		List<SubcontractPaymentDto> results = new ArrayList<SubcontractPaymentDto>();

		for (SubcontractPayment each : subcontractPayments) {
			results.add(new SubcontractPaymentDto(each));
		}
		return results;
	}

	public SubContractInvoiceDto getInvoice() {
		return invoice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getSpendDate() {
		return spendDate;
	}

	public String getRemark() {
		return remark;
	}

	public Long getId() {
		return id;
	}

}
